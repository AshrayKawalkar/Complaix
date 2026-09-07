package com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentResponse {

    private Long id;
    private String name;
    private String description;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
