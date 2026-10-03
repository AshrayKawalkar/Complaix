package com.Ashray.Smart.Complaint.Management.System.Complaint.Service;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.CreateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.UpdateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintAdminResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintUserResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ComplaintService {

    ComplaintAdminResponse createComplaint(CreateComplaintRequest request);
    ComplaintAdminResponse updateComplaint(Long id , UpdateComplaintRequest request);

    void deleteComplaint(Long id);

    ComplaintAdminResponse getComplaintById(Long id);

    @Transactional(readOnly = true)
    Page<ComplaintAdminResponse> allComplaint(ComplaintStatus status,
                                              ComplaintPriority priority,
                                              Long departmentId,
                                              Long categoryId,
                                              Long createdById,
                                              Long assignedToId,
                                              String search,
                                              Pageable pageable);
}
