package com.gametags.gametags.infrastructure.adapters;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import com.gametags.gametags.infrastructure.daos.UserDAO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
import com.gametags.gametags.infrastructure.repositories.ClassificationRepository;
import com.gametags.gametags.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@Repository
@AllArgsConstructor
@Slf4j
public class UserAdapter {
    private UserRepository repo;
    private UserMapper mapper;

    public User update(User user) {
        return mapper.fromEntityToDomain(repo.save(mapper.toEntity(user)));
    }

    public List<User> findAll() {
        return repo.findAll().stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
    }

    public User findById(UUID id) {
        return mapper.fromEntityToDomain(repo.findById(id).orElseGet(() -> UserDAO.builder().build()));
    }

    public User delete(UUID id) {
        User dao = this.findById(id);
        repo.deleteById(dao.getUsername());
        return dao;
    }

    public User findByUsername(String username) {
        return mapper.fromEntityToDomain(repo.findById(username).orElseGet(() -> UserDAO.builder().build()));
    }

    public User create(User user) {
        return mapper.fromEntityToDomain(repo.save(mapper.toEntity(user)));
    }
}
