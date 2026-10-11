package com.Ashray.Smart.Complaint.Management.System.Ai.Dto;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiAnalysisResult {

    // Classification

    private Long recommendedCategoryId;
    private Long recommendedDepartmentId;
    private ComplaintPriority recommendedPriority;


    //Summerization
    private String summary;

    //Confidence
    private Double confidenceScore;

    //Duplicate detection
    private Long duplicateOfId;
    private Double duplicateScore;

    //Metadata
    private String modelUsed;

}
