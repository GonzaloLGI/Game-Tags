package com.gametags.gametags.infrastructure.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.classification.DeleteClassificationUseCase;
import com.gametags.gametags.application.classification.FindAllClassificationsUseCase;
import com.gametags.gametags.application.classification.FindClassificationByIdUseCase;
import com.gametags.gametags.application.classification.UpdateClassificationUseCase;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classification/")
public class ClassificationController {

  @Autowired
  private ClassificationMapper mapper;

  @Autowired
  private CreateClassificationUseCase createUseCase;

  @Autowired
  private DeleteClassificationUseCase deleteUseCase;

  @Autowired
  private FindAllClassificationsUseCase findAllUseCase;

  @Autowired
  private FindClassificationByIdUseCase findByIdUseCase;

  @Autowired
  private UpdateClassificationUseCase updateUseCase;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<ClassificationDTO> createClassification(@RequestBody CreateClassificationInput input) {
    return new ResponseEntity<>(mapper
        .toDto(createUseCase.createClassification(mapper
            .fromDtoToDomain(mapper
                .fromInputToDto(input)))), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<ClassificationDTO> getOneClassification(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneClassification(id)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("La clasificacion a buscar no se encuentra guardada en la base de datos. Prueba con otra");
    }

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<ClassificationDTO>> getAllClassification() {
    List<Classification> list = findAllUseCase.findAllClassifications();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<ClassificationDTO> updateClassification(@RequestBody CreateClassificationInput input) {
    try {
      return new ResponseEntity<>(mapper
          .toDto(updateUseCase.updateClassification(mapper
              .fromDtoToDomain(mapper
                  .fromUpdateInputToDto(input)))), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(
          "La clasificacion a actualizar no se encuentra guardada en la base de datos. Prueba con otra o guarda la actual");
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteClassification(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("La clasificación a borrar no existe. Prueba con otra");
    }
  }
}
