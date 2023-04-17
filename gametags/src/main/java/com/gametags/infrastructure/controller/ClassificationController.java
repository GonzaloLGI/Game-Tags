package com.gametags.infrastructure.controller;

import com.gametags.infrastructure.ClassificationDTO;
import com.gametags.infrastructure.mappers.ClassificationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/classification")
public class ClassificationController {

    private ClassificationMapper mapper;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClassificationDTO> createClassification(@RequestBody ClassificationDTO dto){
        return mapper.toDto(service.createClassification(mapper.toDomain(dto)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClassificationDTO> getOneClassification(@PathVariable UUID id){

    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClassificationDTO>> getAllClassification(){

    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> updateClassification(@RequestBody ClassificationDTO dto){

    }

    @DeleteMapping("/[id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id){

    }
}
