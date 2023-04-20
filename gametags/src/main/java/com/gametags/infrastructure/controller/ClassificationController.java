package com.gametags.infrastructure.controller;

import com.gametags.application.classification.*;
import com.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.domain.Classification;
import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.ClassificationDTO;
import com.gametags.infrastructure.mappers.ClassificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<ClassificationDTO> createClassification(@RequestBody CreateClassificationInput input){
        return new ResponseEntity<>(mapper
                .toDto(createUseCase.createClassification(mapper
                        .fromDtoToDomain(mapper
                                .fromInputToDto(input)))),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClassificationDTO> getOneClassification(@PathVariable UUID id){
        try{
            return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneClassification(id)),HttpStatus.FOUND);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("La clasificacion a buscar no se encuentra guardada en la base de datos. Prueba con otra");
        }

    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClassificationDTO>> getAllClassification(){
         List<Classification> list = findAllUseCase.findAllClassifications();
         return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()),HttpStatus.FOUND);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> updateClassification(@RequestBody CreateClassificationInput input){
        return new ResponseEntity<>(mapper
                .toDto(updateUseCase.updateClassification(mapper
                        .fromDtoToDomain(mapper
                                .fromInputToDto(input)))),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/[id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id){
        try{
            return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteClassification(id)),HttpStatus.ACCEPTED);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("La clasificación a borrar no existe. Prueba con otra");
        }
    }
}
