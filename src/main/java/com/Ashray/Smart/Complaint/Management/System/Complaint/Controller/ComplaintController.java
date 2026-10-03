package com.Ashray.Smart.Complaint.Management.System.Complaint.Controller;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.CreateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.UpdateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintAdminResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import org.springframework.data.domain.Page;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintAdminResponse> createComplaint(@Valid @RequestBody CreateComplaintRequest request) {
        ComplaintAdminResponse complaint = complaintService.createComplaint(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(complaint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintAdminResponse> updateComplaint(@PathVariable Long id, @Valid @RequestBody UpdateComplaintRequest request) {
        ComplaintAdminResponse complaint = complaintService.updateComplaint(id, request);
        return ResponseEntity.ok(complaint);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintAdminResponse> getComplaintById(@PathVariable Long id) {
        ComplaintAdminResponse complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ComplaintAdminResponse>> getAllComplaints(
            @RequestParam(required = false) ComplaintStatus status,
            @RequestParam(required = false) ComplaintPriority priority,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long createdById,
            @RequestParam(required = false) Long assignedToId,
            @RequestParam(required = false) String search,
            Pageable pageable) {

        Page<ComplaintAdminResponse> page = complaintService.allComplaint(
                status,
                priority,
                departmentId,
                categoryId,
                createdById,
                assignedToId,
                search,
                pageable
        );

        return ResponseEntity.ok(page);
    }


    }

