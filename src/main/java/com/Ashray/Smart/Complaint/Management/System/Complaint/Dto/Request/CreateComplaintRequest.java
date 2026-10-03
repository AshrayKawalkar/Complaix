package com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComplaintRequest {

    @NotBlank(message = "Complaint title is required")
    @Size(max = 100, message = "Complaint title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Complaint description is required")
    @Size(max = 500, message = "Complaint description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Department ID is required")
    private Long departmentId;


    private Long categoryId;


    private ComplaintPriority priority;
}
