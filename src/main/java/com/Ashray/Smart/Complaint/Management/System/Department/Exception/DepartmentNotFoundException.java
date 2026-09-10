package com.Ashray.Smart.Complaint.Management.System.Department.Exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("Department with id " + id + " not found");
    }
}
