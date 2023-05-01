package com.gametags.gametags.infrastructure.adapters;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.gametags.infrastructure.repositories.ClassificationRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Repository
@AllArgsConstructor
@Slf4j
public class UserAdapter {
    private UserRepository repo;
    private UserMapper mapper;

    public User update(User user) {
    }

    public List<User> findAll() {
    }

    public User findById(UUID id) {
    }

    public User delete(UUID id) {
    }

    public User findByUsername(String username) {
    }

    public User create(User user) {
    }
}
