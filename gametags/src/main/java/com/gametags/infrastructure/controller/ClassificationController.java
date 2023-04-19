package com.gametags.infrastructure.controller;

import com.gametags.application.classification.*;
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
    private CreateClassificationUseCase createUseCase;
    private DeleteClassificationUseCase deleteUseCase;
    private FindAllClassificationsUseCase findAllUseCase;
    private FindClassificationByIdUseCase findByIdUseCase;
    private UpdateClassificationUseCase updateUseCase;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClassificationDTO> createClassification(@RequestBody ClassificationDTO dto){
        return new ResponseEntity<>(mapper.toDto(createUseCase.createClassification(mapper.toDomain(dto))),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClassificationDTO> getOneClassification(@PathVariable UUID id){
        return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneClassification(id)),HttpStatus.FOUND);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClassificationDTO>> getAllClassification(){
         List<ClassificationDAO> list = findAllUseCase.findAllClassifications();
         return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()),HttpStatus.FOUND);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> updateClassification(@RequestBody ClassificationDTO dto){
        return new ResponseEntity<>(mapper.toDto(updateUseCase.updateClassification(mapper.toDomain(dto))),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/[id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id){
        return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteClassification(id)),HttpStatus.ACCEPTED);
    }
}
