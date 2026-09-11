package com.Ashray.Smart.Complaint.Management.System.Department.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.UpdateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.Department.Exception.DepartmentNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Department.Mapper.DepartmentMapper;
import com.Ashray.Smart.Complaint.Management.System.Department.Repository.DepartmentRepository;
import com.Ashray.Smart.Complaint.Management.System.Department.Service.DepartmentService;
import com.Ashray.Smart.Complaint.Management.System.Department.Spefcification.DepartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;



    @Override
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        Department department= mapper.toEntity(request);

        department.setCreatedAt(LocalDateTime.now());
        department.setUpdatedAt(LocalDateTime.now());

        Department saveDepartment = repository.save(department);

        return mapper.toResponse(saveDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long departmentId) {

        Department department = repository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException( departmentId));

        return mapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long departmentId, UpdateDepartmentRequest request) {

        Department department = repository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException( departmentId));

        mapper.updateEntity(department , request);
        department.setUpdatedAt(LocalDateTime.now());
        Department save = repository.save(department);

        return  mapper.toResponse(save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> allDepartments(Boolean enabled, String search, Pageable pageable) {

        Specification<Department> spec = Specification
                .where(DepartmentSpecification.hasEnabled(enabled))
                .and(DepartmentSpecification.hasName(search));

        Page<Department> departmentPage = repository.findAll(spec, pageable);
        return departmentPage.map(mapper :: toResponse);
    }


    @Override
    public void deleteDepartment(Long departmentId) {

        Department department = repository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException( departmentId));

    }
}
