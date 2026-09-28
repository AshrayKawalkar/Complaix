package com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintStaffResponse {

    private Long id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private ComplaintPriority priority;

    // Relations (summaries)
    private ComplaintAdminResponse.CategorySummary category;
    private ComplaintAdminResponse.DepartmentSummary department;
    private ComplaintAdminResponse.UserSummary createdBy;
    private ComplaintAdminResponse.UserSummary assignedTo;

    // AI assistance for staff
    private String aiSummary;
    private ComplaintPriority aiRecommendedPriority;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}