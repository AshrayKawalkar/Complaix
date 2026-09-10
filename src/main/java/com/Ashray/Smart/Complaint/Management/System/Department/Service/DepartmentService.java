package com.Ashray.Smart.Complaint.Management.System.Department.Service;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.UpdateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    DepartmentResponse getDepartmentById(Long departmentId);

    DepartmentResponse updateDepartment(Long departmentId, UpdateDepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    void deleteDepartment(Long departmentId);


}
