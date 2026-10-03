package com.Ashray.Smart.Complaint.Management.System.Complaint.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.CreateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.UpdateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintAdminResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Category;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Complaint;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Exception.ComplaintNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Mapper.ComplaintMapper;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Repository.CategoryRepository;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Repository.ComplaintRepository;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Service.ComplaintService;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Specification.ComplaintSpecification;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.Department.Exception.DepartmentNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Department.Repository.DepartmentRepository;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.User;
import com.Ashray.Smart.Complaint.Management.System.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private  final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private final ComplaintMapper mapper;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;




    @Override
    @Transactional
    public ComplaintAdminResponse createComplaint(CreateComplaintRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));


        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + request.getCategoryId()));
        }

        User creator = getCurrentUser();
        Complaint complaint = mapper.toEntity(request, department, category, creator);
        Complaint save = complaintRepository.save(complaint);

        return mapper.toAdminResponse(save);


    }

    @Override
    @Transactional
    public ComplaintAdminResponse updateComplaint(Long id, UpdateComplaintRequest request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        Department department = null;
        if(request.getDepartmentId() != null) {
          department= departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));
        }

        Category category = null;
      if(request.getCategoryId() != null) {
            category= categoryRepository.findById(request.getCategoryId())
                  .orElseThrow(() -> new RuntimeException("Category  is not found with Id  " + request.getCategoryId()));

      }

      mapper.updateEntity(complaint,request,department,category);
        Complaint updated = complaintRepository.save(complaint);
        return mapper.toAdminResponse(updated);
    }

    @Override
    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        complaintRepository.delete(complaint);
        

    }

    @Override
    public ComplaintAdminResponse getComplaintById(Long id) {
            Complaint complaint = complaintRepository.findById(id)
                    .orElseThrow(() -> new ComplaintNotFoundException(id));
            return mapper.toAdminResponse(complaint);
    }


    @Transactional(readOnly = true)
    @Override
    public Page<ComplaintAdminResponse> allComplaint(ComplaintStatus status,
                                                     ComplaintPriority priority,
                                                     Long departmentId,
                                                     Long categoryId,
                                                     Long createdById,
                                                     Long assignedToId,
                                                     String search,
                                                     Pageable pageable) {

        Specification<Complaint> spec = Specification
                .where(ComplaintSpecification.hasStatus(status))
                .and(ComplaintSpecification.hasPriority(priority))
                .and(ComplaintSpecification.hasDepartment(departmentId))
                .and(ComplaintSpecification.hasCategory(categoryId))
                .and(ComplaintSpecification.createdBy(createdById))
                .and(ComplaintSpecification.assignedTo(assignedToId))
                .and(ComplaintSpecification.titleContains(search));


        Page<Complaint> complaintPage = complaintRepository.findAll(spec,pageable);
              return complaintPage
                      .map(mapper::toAdminResponse);
    }


    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Authenticated user not found: " + email));
    }




}


