package com.gametags.gametags.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClassificationDTO {
    private UUID id;
    private String system;
    private String country;
    private String tag;
    private String url;
}
