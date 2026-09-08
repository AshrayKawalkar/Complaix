package com.Ashray.Smart.Complaint.Management.System.Department.Controller;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;
import com.Ashray.Smart.Complaint.Management.System.Department.Service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;


    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponse> createDepartment(
                     @Valid
                     @RequestBody CreateDepartmentRequest request) {

        DepartmentResponse department = departmentService.createDepartment(request);

        return new ResponseEntity<>(department , HttpStatus.CREATED);
    }




    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId) {

        DepartmentResponse department = departmentService.getDepartmentById(departmentId);

        return new ResponseEntity<>(department , HttpStatus.OK);
    }

}
