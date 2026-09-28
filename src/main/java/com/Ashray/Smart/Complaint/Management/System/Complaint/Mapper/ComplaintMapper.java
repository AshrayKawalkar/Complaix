package com.Ashray.Smart.Complaint.Management.System.Complaint.Mapper;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintAdminResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintStaffResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Response.ComplaintUserResponse;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.CreateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Dto.Request.UpdateComplaintRequest;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Category;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Complaint;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper {

    // Request -> Entity

    public Complaint toEntity(CreateComplaintRequest request,
                              Department department,
                              Category category,
                              User creator) {

        if (request == null) {
            return null;
        }

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setDepartment(department);
        complaint.setCategory(category);
        complaint.setCreatedBy(creator);

        if (request.getPriority() != null) {
            complaint.setPriority(request.getPriority());
        }


        return complaint;
    }



    public void updateEntity(Complaint complaint,
                             UpdateComplaintRequest request,
                             Department department,
                             Category category) {

        if (complaint == null || request == null) {
            return;
        }

        if (request.getTitle() != null) {
            complaint.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            complaint.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            complaint.setPriority(request.getPriority());
        }
        if (department != null) {
            complaint.setDepartment(department);
        }
        if (category != null) {
            complaint.setCategory(category);
        }
    }

   // Entity -> Response

    public ComplaintUserResponse toUserResponse(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        ComplaintUserResponse response = new ComplaintUserResponse();
        response.setId(complaint.getId());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setStatus(complaint.getStatus());
        response.setCreatedAt(complaint.getCreatedAt());
        response.setUpdatedAt(complaint.getUpdatedAt());
        return response;
    }

    public ComplaintStaffResponse toStaffResponse(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        ComplaintStaffResponse response = new ComplaintStaffResponse();
        response.setId(complaint.getId());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setStatus(complaint.getStatus());
        response.setPriority(complaint.getPriority());

        response.setCategory(toCategorySummary(complaint.getCategory()));
        response.setDepartment(toDepartmentSummary(complaint.getDepartment()));
        response.setCreatedBy(toUserSummary(complaint.getCreatedBy()));
        response.setAssignedTo(toUserSummary(complaint.getAssignedTo()));

        response.setAiSummary(complaint.getAiSummary());
        response.setAiRecommendedPriority(complaint.getAiRecommendedPriority());

        response.setCreatedAt(complaint.getCreatedAt());
        response.setUpdatedAt(complaint.getUpdatedAt());
        return response;
    }

    public ComplaintAdminResponse toAdminResponse(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        ComplaintAdminResponse response = new ComplaintAdminResponse();
        response.setId(complaint.getId());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setStatus(complaint.getStatus());
        response.setPriority(complaint.getPriority());

        response.setCategory(toCategorySummary(complaint.getCategory()));
        response.setDepartment(toDepartmentSummary(complaint.getDepartment()));
        response.setCreatedBy(toUserSummary(complaint.getCreatedBy()));
        response.setAssignedTo(toUserSummary(complaint.getAssignedTo()));

        // AI recommendations
        response.setAiRecommendedCategory(toCategorySummary(complaint.getAiRecommendedCategory()));
        response.setAiRecommendedDepartment(toDepartmentSummary(complaint.getAiRecommendedDepartment()));
        response.setAiRecommendedPriority(complaint.getAiRecommendedPriority());
        response.setAiSummary(complaint.getAiSummary());
        response.setAiConfidenceScore(complaint.getAiConfidenceScore());

        // AI duplicate detection
        response.setAiDuplicateOf(toComplaintSummary(complaint.getAiDuplicateOf()));
        response.setAiDuplicateScore(complaint.getAiDuplicateScore());
        response.setAiDuplicateChecked(complaint.getAiDuplicateChecked());

        // AI metadata
        response.setAiProcessed(complaint.getAiProcessed());
        response.setAiProcessedAt(complaint.getAiProcessedAt());

        // Audit
        response.setCreatedAt(complaint.getCreatedAt());
        response.setUpdatedAt(complaint.getUpdatedAt());

        return response;
    }


    //  Helpers — Entity → nested Summary DTO


    private ComplaintAdminResponse.CategorySummary toCategorySummary(Category category) {
        if (category == null) {
            return null;
        }
        ComplaintAdminResponse.CategorySummary summary = new ComplaintAdminResponse.CategorySummary();
        summary.setId(category.getId());
        summary.setName(category.getName());
        return summary;
    }

    private ComplaintAdminResponse.DepartmentSummary toDepartmentSummary(Department department) {
        if (department == null) {
            return null;
        }
        ComplaintAdminResponse.DepartmentSummary summary = new ComplaintAdminResponse.DepartmentSummary();
        summary.setId(department.getId());
        summary.setName(department.getName());
        return summary;
    }

    private ComplaintAdminResponse.UserSummary toUserSummary(User user) {
        if (user == null) {
            return null;
        }
        ComplaintAdminResponse.UserSummary summary = new ComplaintAdminResponse.UserSummary();
        summary.setId(user.getId());
        summary.setName(user.getName());
        summary.setEmail(user.getEmail());
        return summary;
    }

    private ComplaintAdminResponse.ComplaintSummary toComplaintSummary(Complaint complaint) {
        if (complaint == null) {
            return null;
        }
        ComplaintAdminResponse.ComplaintSummary summary = new ComplaintAdminResponse.ComplaintSummary();
        summary.setId(complaint.getId());
        summary.setTitle(complaint.getTitle());
        return summary;
    }
}