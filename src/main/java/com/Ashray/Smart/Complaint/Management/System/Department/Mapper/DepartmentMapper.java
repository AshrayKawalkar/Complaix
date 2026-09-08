package com.Ashray.Smart.Complaint.Management.System.Department.Mapper;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {



    public DepartmentResponse toResponse(Department department) {

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        departmentResponse.setDescription(department.getDescription());
        departmentResponse.setEnabled(department.isEnabled());
        departmentResponse.setCreatedAt(department.getCreatedAt());
        departmentResponse.setUpdatedAt(department.getUpdatedAt());
        return departmentResponse;
    }


    public Department toEntity(CreateDepartmentRequest createDepartmentRequest) {

        if(createDepartmentRequest == null) {
            return null;
        }

        Department department = new Department();
        department.setName(createDepartmentRequest.getName());
        department.setDescription(createDepartmentRequest.getDescription());

        return department;
    }

}
