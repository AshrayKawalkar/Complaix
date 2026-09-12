package com.Ashray.Smart.Complaint.Management.System.Complaint.Entity;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status = ComplaintStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintPriority priority = ComplaintPriority.MEDIUM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_recommended_category_id")
    private Category aiRecommendedCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_recommended_department_id")
    private Department aiRecommendedDepartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_recommended_priority")
    private ComplaintPriority aiRecommendedPriority;

    @Column(name = "ai_summary", length = 500)
    private String aiSummary;

    @Column(name = "ai_confidence_score")
    private Double aiConfidenceScore;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_duplicate_of_id")
    private Complaint aiDuplicateOf;

    @Column(name = "ai_duplicate_score")
    private Double aiDuplicateScore;

    @Column(name = "ai_duplicate_checked", nullable = false)
    private Boolean aiDuplicateChecked = false;


    @Column(name = "ai_processed", nullable = false)
    private Boolean aiProcessed = false;

    @Column(name = "ai_processed_at")
    private LocalDateTime aiProcessedAt;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}