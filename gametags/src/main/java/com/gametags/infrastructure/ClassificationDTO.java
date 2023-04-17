package com.gametags.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationDTO {
    private UUID Id;
    private String system;
    private String country;
    private String tag;
    private String url;
}
