package com.Ashray.Smart.Complaint.Management.System.Department.Controller;

import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.CreateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Request.UpdateDepartmentRequest;
import com.Ashray.Smart.Complaint.Management.System.Department.Dto.Response.DepartmentResponse;
import com.Ashray.Smart.Complaint.Management.System.Department.Service.DepartmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dept")
public class DepartmentController {

    private final DepartmentService departmentService;


    @PostMapping()
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {

        DepartmentResponse department = departmentService.createDepartment(request);

        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId) {

        DepartmentResponse department = departmentService.getDepartmentById(departmentId);

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody UpdateDepartmentRequest request) {


        DepartmentResponse updateDepartment = departmentService.updateDepartment(departmentId, request);

        return new ResponseEntity<DepartmentResponse>(updateDepartment, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Page<DepartmentResponse>> allDepartments(
                     @RequestParam(required = false ) Boolean enabled,
                     @RequestParam(required = false) String search , Pageable pageable) {

        Page<DepartmentResponse> departments = departmentService.allDepartments(enabled, search, pageable);

        return new ResponseEntity<>(departments , HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {

        departmentService.deleteDepartment(departmentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
