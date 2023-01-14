package com.celilSecurity.securityjwtexample.service;


import com.celilSecurity.securityjwtexample.dto.UserDto;
import com.celilSecurity.securityjwtexample.dto.UserRequest;
import com.celilSecurity.securityjwtexample.dto.UserResponse;
import com.celilSecurity.securityjwtexample.enums.Role;
import com.celilSecurity.securityjwtexample.model.User;
import com.celilSecurity.securityjwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public UserResponse save(UserDto userDto) {

        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getLastName())
                .lastName(userDto.getLastName())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();

    }

    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();
    }
}
