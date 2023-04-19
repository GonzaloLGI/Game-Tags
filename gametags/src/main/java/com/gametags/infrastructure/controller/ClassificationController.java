package com.gametags.infrastructure.controller;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.ClassificationDTO;
import com.gametags.infrastructure.mappers.ClassificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/classification")
@RequiredArgsConstructor
public class ClassificationController {

    private ClassificationMapper mapper;
    private ClassificationService service;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClassificationDTO> createClassification(@RequestBody ClassificationDTO dto){
        return new ResponseEntity<>(mapper.toDto(service.createClassification(mapper.toDomain(dto))),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClassificationDTO> getOneClassification(@PathVariable UUID id){
        return new ResponseEntity<>(mapper.toDto(service.findOneClassification(id)),HttpStatus.FOUND);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClassificationDTO>> getAllClassification(){
         List<ClassificationDAO> list = service.findAllClassifications();
         return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()),HttpStatus.FOUND);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> updateClassification(@RequestBody ClassificationDTO dto){
        return new ResponseEntity<>(mapper.toDto(service.updateClassification(mapper.toDomain(dto))),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/[id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id){
        return new ResponseEntity<>(mapper.toDto(service.deleteClassification(id)),HttpStatus.ACCEPTED);
    }
}
