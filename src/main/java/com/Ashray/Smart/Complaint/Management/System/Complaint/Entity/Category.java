package com.Ashray.Smart.Complaint.Management.System.Complaint.Entity;

import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , length = 100)
    private String name;

    @Column(nullable = false , length = 500)
    private String description;

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToOne(
            fetch = FetchType.LAZY

    )
    @JoinColumn(name = "default_department_id", nullable = false)
    private Department defaultDepartment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
