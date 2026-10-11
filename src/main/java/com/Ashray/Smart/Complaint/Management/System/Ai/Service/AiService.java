package com.Ashray.Smart.Complaint.Management.System.Ai.Service;

import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisRequest;
import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisResult;

public interface AiService {
    AiAnalysisResult analyze(AiAnalysisRequest request);
}
