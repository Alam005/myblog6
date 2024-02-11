package com.myblog.myblog6.service;

import com.myblog.myblog6.payload.SignUpDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> registerUser(SignUpDto signUpDto);

}
