package com.Ashray.Smart.Complaint.Management.System.Ai.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisRequest;
import com.Ashray.Smart.Complaint.Management.System.Ai.Dto.AiAnalysisResult;
import com.Ashray.Smart.Complaint.Management.System.Ai.Service.AiService;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "ai.provider", havingValue = "mock", matchIfMissing = true)
public class MockAiServiceImpl implements AiService {

    private static final String MODEL_NAME = "mock-v1";

    @Override
    public AiAnalysisResult analyze(AiAnalysisRequest request) {

        log.info("[MockAI] Analyzing complaint: {}", request.getTitle());

        // ─── 1. Pick first available category ───
        Long recommendedCategoryId = null;
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            recommendedCategoryId = request.getCategories().get(0).getId();
        }

        // ─── 2. Pick first available department ───
        Long recommendedDepartmentId = null;
        if (request.getDepartments() != null && !request.getDepartments().isEmpty()) {
            recommendedDepartmentId = request.getDepartments().get(0).getId();
        }

        // ─── 3. Recommend priority based on keyword heuristics ───
        ComplaintPriority recommendedPriority = guessPriority(
                request.getTitle(),
                request.getDescription()
        );

        // ─── 4. Generate a short summary ───
        String summary = generateSummary(request.getDescription());

        // ─── 5. Fake duplicate detection ───
        Long duplicateOfId = null;
        Double duplicateScore = 0.0;

        // ─── 6. Confidence ───
        double confidence = 0.85;

        return AiAnalysisResult.builder()
                .recommendedCategoryId(recommendedCategoryId)
                .recommendedDepartmentId(recommendedDepartmentId)
                .recommendedPriority(recommendedPriority)
                .summary(summary)
                .confidenceScore(confidence)
                .duplicateOfId(duplicateOfId)
                .duplicateScore(duplicateScore)
                .modelUsed(MODEL_NAME)
                .build();
    }

    // ═════════════════════════════════════════════
    //  Keyword heuristics
    // ═════════════════════════════════════════════

    private ComplaintPriority guessPriority(String title, String description) {
        String text = ((title == null ? "" : title) + " " + (description == null ? "" : description))
                .toLowerCase();

        if (containsAny(text, "outage", "down", "fire", "flood", "urgent", "emergency", "critical")) {
            return ComplaintPriority.CRITICAL;
        }
        if (containsAny(text, "broken", "not working", "failed", "error", "issue")) {
            return ComplaintPriority.HIGH;
        }
        if (containsAny(text, "slow", "delay", "minor", "small")) {
            return ComplaintPriority.LOW;
        }
        return ComplaintPriority.MEDIUM;
    }

    private boolean containsAny(String text, String... keywords) {
        for (String kw : keywords) {
            if (text.contains(kw)) return true;
        }
        return false;
    }

    private String generateSummary(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }
        String trimmed = description.trim();
        return trimmed.length() <= 200 ? trimmed : trimmed.substring(0, 200) + "...";
    }
}