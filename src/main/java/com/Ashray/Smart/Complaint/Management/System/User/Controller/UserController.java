package com.Ashray.Smart.Complaint.Management.System.User.Controller;

import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.RegisterUserRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.RegisterUserResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponce> registerUserRequest (
                                                @Valid
                                                @RequestBody RegisterUserRequest request
                                                          ) {

        RegisterUserResponce register = userService.registerUser(request);

        return new ResponseEntity<>(register , HttpStatus.CREATED);
    }
}
