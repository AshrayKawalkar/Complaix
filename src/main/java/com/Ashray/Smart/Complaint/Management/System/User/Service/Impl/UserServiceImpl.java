package com.Ashray.Smart.Complaint.Management.System.User.Service.Impl;

import com.Ashray.Smart.Complaint.Management.System.Security.Jwt.JwtService;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.LoginRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Request.RegisterUserRequest;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.LoginResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Dto.Response.RegisterUserResponce;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.Role;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.User;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.EmailAlredyExitsException;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.InvalidCredentialsException;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.PhoneNumberAlreadyExitsException;
import com.Ashray.Smart.Complaint.Management.System.User.Repository.UserRepository;
import com.Ashray.Smart.Complaint.Management.System.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    @Override
    public RegisterUserResponce registerUser(RegisterUserRequest registerUserRequest)  {

        if(userRepository.existsByEmail(registerUserRequest.getEmail())){
            throw new EmailAlredyExitsException("Email already exists");
        }

        if(userRepository.existsByPhoneNumber(registerUserRequest.getPhoneNumber())){
            throw new PhoneNumberAlreadyExitsException("Phone number already exists");
        }

        User user = new User();
        user.setName(registerUserRequest.getName());
        user.setEmail(registerUserRequest.getEmail());
        user.setPhoneNumber(registerUserRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);


        RegisterUserResponce responce = new RegisterUserResponce();
        responce.setMessage("User registered successfully");
        responce.setId(user.getId());
        responce.setName(user.getName());
        responce.setEmail(user.getEmail());
        responce.setPhoneNumber(user.getPhoneNumber());


        return responce;


    }

    @Override
    public LoginResponce loginUser(LoginRequest loginRequest) {

        User user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->  new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }



        String token = jwtService.generateToken(user.getEmail());


        LoginResponce responce = new LoginResponce();
        responce.setToken(token);

        return responce;


    }
}
