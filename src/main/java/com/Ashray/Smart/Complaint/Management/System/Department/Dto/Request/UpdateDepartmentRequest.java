package com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    private String name;

    private String description;

}
