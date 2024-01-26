package com.template.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.jwt.dtos.TokenDTO;
import com.template.jwt.dtos.UserDTO;
import com.template.jwt.models.UserEntity;
import com.template.jwt.repositoties.UserRepository;
import com.template.jwt.services.CustomeUserDetailsService;
import com.template.jwt.untils.JwtUntils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUntils jwtUntils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/resgister")
    public ResponseEntity<String> registerTest(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @PostMapping("/resgister")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        userRepository.save(new UserEntity(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword())));
        return new ResponseEntity<>("Success!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new TokenDTO("Invalid username or password."), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(userDTO.getUsername());
        String token = jwtUntils.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }
}
