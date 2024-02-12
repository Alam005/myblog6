package com.myblog.myblog6.service.impl;

import com.myblog.myblog6.entity.Role;
import com.myblog.myblog6.entity.User;
import com.myblog.myblog6.payload.SignUpDto;
import com.myblog.myblog6.repository.RoleRepository;
import com.myblog.myblog6.repository.UserRepository;
import com.myblog.myblog6.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository =roleRepository;
    }

    @Override
    public ResponseEntity<String> registerUser(SignUpDto signUpDto) {
      if(userRepository.existsByUsername(signUpDto.getUsername())){
          return ResponseEntity.badRequest().body("Username is already taken!");
      } else if (userRepository.existsByEmail(signUpDto.getEmail())) {
          return ResponseEntity.badRequest().body("Email is already taken!");
      }
      User user = new User();
      user.setUsername(signUpDto.getUsername());
      user.setEmail(signUpDto.getEmail());
      user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
      Role roles = roleRepository.findByName(signUpDto.getRoleType()).get();

        Set<Role> convertRoleToSet = new HashSet<>();
        convertRoleToSet.add(roles);
        user.setRoles(convertRoleToSet);

        userRepository.save(user);
      return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

}
