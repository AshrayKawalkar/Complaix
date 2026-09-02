package com.Ashray.Smart.Complaint.Management.System.User.Service;

import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.LoginRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.RegisterUserRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.LoginResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.RegisterUserResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.EmailAlredyExitsException;

import javax.security.auth.login.LoginContext;


public interface UserService {


    RegisterUserResponce registerUser(RegisterUserRequest registerUserRequest);

    LoginResponce loginUser(LoginRequest loginRequest);
}
