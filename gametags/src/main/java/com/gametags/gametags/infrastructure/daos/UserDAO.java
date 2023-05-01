package com.gametags.gametags.infrastructure.daos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDAO {
    private UUID id;
    @Id
    private String username;
    private String email;
    private String password;
    private List<String> comments;
    private String country;
}