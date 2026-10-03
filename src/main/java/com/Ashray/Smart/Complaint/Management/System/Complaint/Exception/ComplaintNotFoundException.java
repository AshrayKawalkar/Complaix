package com.Ashray.Smart.Complaint.Management.System.Complaint.Exception;

public class ComplaintNotFoundException extends RuntimeException{
    public ComplaintNotFoundException(Long id) {
        super("Complaint not found with id" + id);
    }
}
