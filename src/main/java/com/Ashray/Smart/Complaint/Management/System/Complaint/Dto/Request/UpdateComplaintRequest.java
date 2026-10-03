package com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateComplaintRequest {

    @Size(max=100, message = "Complaint title must not exceed 100 characters")
    private String title;


    @Size(max=500, message = "Complaint description must not exceed 500 characters")
    private String description;

    private ComplaintPriority priority;

    private Long departmentId;

    private Long categoryId;
}
