package com.gametags.gametags.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Classification {
    private UUID id;
    private String system;
    private String country;
    private String tag;
    private String url;
}
