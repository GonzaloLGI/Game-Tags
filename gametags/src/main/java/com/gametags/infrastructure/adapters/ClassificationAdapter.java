package com.gametags.infrastructure.adapters;

import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.repositories.ClassificationRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Repository
public class ClassificationAdapter {

    private ClassificationRepository repo;

    public ClassificationDAO create(ClassificationDAO dao) {
        return repo.save(dao);
    }

    public ClassificationDAO findById(UUID id) {
        return repo.findById(id).orElseGet(() -> ClassificationDAO.builder().build());
    }

    public List<ClassificationDAO> findAll() {
        return repo.findAll();
    }

    public ClassificationDAO update(ClassificationDAO dao) {
        return repo.save(dao);
    }

    public ClassificationDAO delete(UUID id) {
        ClassificationDAO dao = this.findById(id);
        repo.deleteById(id);
        return dao;
    }
}
