package com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintUserResponse {

    private Long id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}