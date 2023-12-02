package com.gametags.gametags.application.user.update_password;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdatePasswordUseCase {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO updatePassword(UpdatePasswordInput input) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = service.findOneUserByUsername(username);
        log.info("EXISTING PASSWORD: " + user.getPassword());
        String newPasswordEncoded = passwordEncoder.encode(input.getNewPassword());
        User changedUser = User.builder()
                .id(user.getId())
                .roles(user.getRoles())
                .email(user.getEmail())
                .country(user.getCountry())
                .username(user.getUsername())
                .password(newPasswordEncoded)
                .build();
        if(passwordEncoder.matches(input.getExistingPassword(),user.getPassword())){
            log.info("New password: " + newPasswordEncoded);
            service.updateUser(changedUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(changedUser.getUsername(), input.getNewPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authenticationManager.authenticate(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new AuthResponseDTO(token, changedUser.getUsername());
        }else{
            throw new RuntimeException("Password is not correct");
        }
    }
}
