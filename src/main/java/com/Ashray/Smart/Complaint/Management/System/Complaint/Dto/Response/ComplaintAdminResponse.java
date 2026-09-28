package com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintAdminResponse {

    private Long id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private ComplaintPriority priority;

    // Relations
    private CategorySummary category;
    private DepartmentSummary department;
    private UserSummary createdBy;
    private UserSummary assignedTo;

    // AI recommendations
    private CategorySummary aiRecommendedCategory;
    private DepartmentSummary aiRecommendedDepartment;
    private ComplaintPriority aiRecommendedPriority;
    private String aiSummary;
    private Double aiConfidenceScore;

    // AI duplicate detection
    private ComplaintSummary aiDuplicateOf;
    private Double aiDuplicateScore;
    private Boolean aiDuplicateChecked;

    // AI metadata
    private Boolean aiProcessed;
    private LocalDateTime aiProcessedAt;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

 
    @Data
    public static class CategorySummary {
        private Long id;
        private String name;
    }

    @Data
    public static class DepartmentSummary {
        private Long id;
        private String name;
    }

    @Data
    public static class UserSummary {
        private Long id;
        private String name;
        private String email;
    }

    @Data
    public static class ComplaintSummary {
        private Long id;
        private String title;
    }
}