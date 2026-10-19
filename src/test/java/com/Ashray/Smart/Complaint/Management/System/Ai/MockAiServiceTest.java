package com.Ashray.Smart.Complaint.Management.System.Ai;

import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisRequest;
import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisResult;
import com.Ashray.Smart.Complaint.Management.System.Ai.Service.AiService;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MockAiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    void shouldReturnCriticalForOutageKeyword() {
        AiAnalysisRequest req = AiAnalysisRequest.builder()
                .title("Server outage in Building B")
                .description("Server down for 2 hours, affecting all users")
                .categories(List.of())
                .departments(List.of())
                .build();

        AiAnalysisResult result = aiService.analyze(req);

        assertEquals(ComplaintPriority.CRITICAL, result.getRecommendedPriority());
        assertEquals("mock-v1", result.getModelUsed());
        assertEquals(0.85, result.getConfidenceScore());
        assertNotNull(result.getSummary());
    }

    @Test
    void shouldReturnLowForMinorIssue() {
        AiAnalysisRequest req = AiAnalysisRequest.builder()
                .title("Slow printer response")
                .description("The printer takes longer than usual to respond")
                .categories(List.of())
                .departments(List.of())
                .build();

        AiAnalysisResult result = aiService.analyze(req);

        assertEquals(ComplaintPriority.LOW, result.getRecommendedPriority());
    }
}