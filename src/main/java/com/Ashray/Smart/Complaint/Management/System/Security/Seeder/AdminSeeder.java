package com.Ashray.Smart.Complaint.Management.System.Security.Seeder;

import com.Ashray.Smart.Complaint.Management.System.User.Entity.Role;
import com.Ashray.Smart.Complaint.Management.System.User.Entity.User;
import com.Ashray.Smart.Complaint.Management.System.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.phone}")
    private String adminPhone;

    @Override
    public void run(String... args) {

        // Check if an admin already exists
        boolean adminExists = userRepository.findByEmail(adminEmail).isPresent();

        if (adminExists) {
            log.info("Admin user already exists. Skipping seed.");
            return;
        }

        User admin = new User();
        admin.setName("System Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setPhoneNumber(adminPhone);
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);

        userRepository.save(admin);

        log.info("✅ Default admin user created: {}", adminEmail);
    }
}