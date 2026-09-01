package com.Ashray.Smart.Complaint.Management.System.User.Dto.Response;

import lombok.Data;

@Data
public class RegisterUserResponce {

    private String message;

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;
}
