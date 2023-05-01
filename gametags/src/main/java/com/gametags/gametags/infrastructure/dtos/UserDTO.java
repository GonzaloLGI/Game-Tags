package com.gametags.gametags.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private List<String> comments;
    private String country;
}
