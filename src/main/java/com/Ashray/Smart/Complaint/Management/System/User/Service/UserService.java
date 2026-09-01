package com.Ashray.Smart.Complaint.Management.System.User.Service;

import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.RegisterUserRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.RegisterUserResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.EmailAlredyExitsException;


public interface UserService {


    RegisterUserResponce registerUser(RegisterUserRequest registerUserRequest) throws EmailAlredyExitsException;
}
