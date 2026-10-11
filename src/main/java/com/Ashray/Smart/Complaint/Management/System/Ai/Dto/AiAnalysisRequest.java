package com.Ashray.Smart.Complaint.Management.System.Ai.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAnalysisRequest {
    private  String title;
    private  String description;
    private List<CategoryOption> categories;
    private List<DepartmentOption> departments;
    private List<ComplaintOption> complaints;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepartmentOption {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static  class CategoryOption {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ComplaintOption {
        private Long id;
        private String title;

    }
}
