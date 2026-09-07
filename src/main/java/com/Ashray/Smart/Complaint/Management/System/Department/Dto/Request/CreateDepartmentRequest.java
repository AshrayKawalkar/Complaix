package com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max=100, message = "Department name must not exceed 100 characters")
    private String name;

    @Size(max=255, message = "Department description must not exceed 255 characters")
    private String description;
}
