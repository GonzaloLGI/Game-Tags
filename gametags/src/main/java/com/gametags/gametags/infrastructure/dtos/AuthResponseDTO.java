package com.gametags.gametags.infrastructure.dtos;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken){
      this.accessToken = accessToken;
    }
}