package com.gametags.gametags.infrastructure.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.classification.*;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/classification/")
@Slf4j
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

  @Autowired
  private AddTagImageUseCase addTagImageUseCase;

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
      log.info("La clasificacion a buscar no se encuentra guardada en la base de datos. Prueba con otra");
      return new ResponseEntity<>(ClassificationDTO.builder().build(),HttpStatus.NOT_FOUND);
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
      log.info("La clasificacion a actualizar no se encuentra guardada en la base de datos. Prueba con otra o guarda la actual");
      return new ResponseEntity<>(ClassificationDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<ClassificationDTO> deleteClassification(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteClassification(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      log.info("La clasificación a borrar no existe. Prueba con otra");
      return new ResponseEntity<>(ClassificationDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/{id}/image/{gameId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<ClassificationDTO> addCover(@RequestPart(name="image") MultipartFile image, @PathVariable UUID id, @PathVariable UUID gameId) throws IOException {
    return new ResponseEntity<>(mapper
            .toDto(addTagImageUseCase.addImage(image, id, gameId)), HttpStatus.CREATED);
  }
}
