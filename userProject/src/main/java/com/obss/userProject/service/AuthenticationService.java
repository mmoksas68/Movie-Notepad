package com.obss.userProject.service;


import com.obss.userProject.classes.MyUser;
import com.obss.userProject.classes.Role;
import com.obss.userProject.classes.requestClasses.LoginRequest;
import com.obss.userProject.classes.requestClasses.RoleName;
import com.obss.userProject.classes.requestClasses.SignUpRequest;
import com.obss.userProject.dao.RoleRepository;
import com.obss.userProject.dao.UserRepository;
import com.obss.userProject.payload.JwtAuthenticationResponse;
import com.obss.userProject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    public ResponseEntity<?> register(SignUpRequest signUpRequest){


        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity( "Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(  "Email Address already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        MyUser user = new MyUser(signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getName(), signUpRequest.getLastName(), signUpRequest.getPassword(),
                true, null, 1);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
/*
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByName(RoleName.ROLE_USER));
        role.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
        user.setRoles(role);
*/
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER);

        user.setRoles(Collections.singleton(userRole));
        MyUser result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return (ResponseEntity<?>) ResponseEntity.ok(new ResponseEntity( "User registered successfully", HttpStatus.OK)).created(location);
    }


    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
