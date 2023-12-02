package com.gametags.gametags.infrastructure.dtos;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String username;

    public AuthResponseDTO(String accessToken, String userName){
      this.accessToken = accessToken;
      this.username = userName;
    }
}
