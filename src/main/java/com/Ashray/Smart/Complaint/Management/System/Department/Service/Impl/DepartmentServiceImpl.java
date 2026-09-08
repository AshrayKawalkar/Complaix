package com.Ashray.Smart.Complaint.Management.System.Department.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.Department.Exception.DepartmentNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.Department.Mapper.DepartmentMapper;
import com.Ashray.Smart.Complaint.Management.System.Department.Repository.DepartmentRepository;
import com.Ashray.Smart.Complaint.Management.System.Department.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + departmentId));

        return mapper.toResponse(department);
    }
}
