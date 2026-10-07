package com.Ashray.Smart.Complaint.Management.System.Complaint.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.CreateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.UpdateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintAdminResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Category;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Complaint;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Exception.ComplaintAccessDeniedException;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Exception.ComplaintNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Mapper.ComplaintMapper;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Repository.CategoryRepository;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Repository.ComplaintRepository;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Service.ComplaintService;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Specification.ComplaintSpecification;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.Department.Exception.DepartmentNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Department.Repository.DepartmentRepository;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.Role;
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

    private final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private final ComplaintMapper mapper;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;


    //  CREATE


    @Override
    @Transactional
    public ComplaintAdminResponse createComplaint(CreateComplaintRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException(
                            "Category not found with id " + request.getCategoryId()));
        }

        User creator = getCurrentUser();
        Complaint complaint = mapper.toEntity(request, department, category, creator);
        Complaint saved = complaintRepository.save(complaint);
        return mapper.toAdminResponse(saved);
    }

    //Update

    @Override
    @Transactional
    public ComplaintAdminResponse updateComplaint(Long id, UpdateComplaintRequest request) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        assertCanAccessComplaint(complaint);

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException(
                            "Category is not found with Id " + request.getCategoryId()));
        }

        mapper.updateEntity(complaint, request, department, category);
        Complaint updated = complaintRepository.save(complaint);
        return mapper.toAdminResponse(updated);
    }

//Delete

    @Override
    @Transactional
    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        complaintRepository.delete(complaint);
    }


    //  READ ONE   --> RBAC check


    @Override
    @Transactional(readOnly = true)
    public ComplaintAdminResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        assertCanAccessComplaint(complaint);
        return mapper.toAdminResponse(complaint);
    }


    //  READ ALL  ---> RBAC filter


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
        User currentUser = getCurrentUser();
        Role role = currentUser.getRole();

        Specification<Complaint> spec = Specification
                .where(ComplaintSpecification.hasStatus(status))
                .and(ComplaintSpecification.hasPriority(priority))
                .and(ComplaintSpecification.hasDepartment(departmentId))
                .and(ComplaintSpecification.hasCategory(categoryId))
                .and(ComplaintSpecification.titleContains(search));

        switch (role) {
            case USER -> spec = spec.and(
                    ComplaintSpecification.createdBy(currentUser.getId())
            );

            case STAFF -> spec = spec.and(
                    ComplaintSpecification.createdBy(currentUser.getId())
                            .or(ComplaintSpecification.assignedTo(currentUser.getId()))
            );

            case ADMIN -> {
                if (createdById != null) {
                    spec = spec.and(ComplaintSpecification.createdBy(createdById));
                }
                if (assignedToId != null) {
                    spec = spec.and(ComplaintSpecification.assignedTo(assignedToId));
                }
            }
        }

        return complaintRepository.findAll(spec, pageable)
                .map(mapper::toAdminResponse);
    }


    //  HELPERS


    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Authenticated user not found: " + email));
    }


    // * Enforces RBAC on a single complaint.
    // * ADMIN  → always allowed
    //* STAFF  → allowed if creator OR assignee
    // * USER   → allowed only if creator

    private void assertCanAccessComplaint(Complaint complaint) {

        User currentUser = getCurrentUser();
        Role role = currentUser.getRole();

        switch (role) {

            case ADMIN -> {
                // Admin can access everything
            }

            case STAFF -> {
                boolean isCreator = complaint.getCreatedBy() != null
                        && complaint.getCreatedBy().getId().equals(currentUser.getId());

                boolean isAssignee = complaint.getAssignedTo() != null
                        && complaint.getAssignedTo().getId().equals(currentUser.getId());

                if (!isCreator && !isAssignee) {
                    throw new ComplaintAccessDeniedException(
                            "You do not have permission to access this complaint");
                }
            }

            case USER -> {
                boolean isCreator = complaint.getCreatedBy() != null
                        && complaint.getCreatedBy().getId().equals(currentUser.getId());

                if (!isCreator) {
                    throw new ComplaintAccessDeniedException(
                            "You do not have permission to access this complaint");
                }
            }
        }
    }
}