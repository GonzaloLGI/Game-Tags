package com.gametags.gametags.infrastructure.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.videogame.*;
import com.gametags.gametags.application.videogame.add_new_classification.AddNewClassificationUseCase;
import com.gametags.gametags.application.videogame.create_videogame.CreateVideoGameInput;
import com.gametags.gametags.application.videogame.create_videogame.CreateVideoGameUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterByDeveloperUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterByPlatformsUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterBySystemUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterByTagUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.gametags.infrastructure.mappers.VideoGameMapper;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

@RestController
@RequestMapping("/videogame/")
public class VideoGameController {

  @Autowired
  private VideoGameMapper mapper;

  @Autowired
  private ClassificationMapper classMapper;

  @Autowired
  private FindVideoGameByIdUseCase findByIdUseCase;

  @Autowired
  private FindAllVideoGamesUseCase findAllUseCase;

  @Autowired
  private UpdateVideoGameUseCase updateUseCase;

  @Autowired
  private DeleteVideoGameUseCase deleteUseCase;

  @Autowired
  private CreateVideoGameUseCase createUseCase;

  @Autowired
  private FindVideoGameByNameUseCase findByNameUseCase;

  @Autowired
  private FilterByDeveloperUseCase filterByDeveloperUseCase;

  @Autowired
  private FilterByPlatformsUseCase filterByPlatformsUseCase;

  @Autowired
  private FilterByTagUseCase filterByTagUseCase;

  @Autowired
  private FilterBySystemUseCase filterBySystemUseCase;

  @Autowired
  private LatestVideogamesUseCase latestVideogamesUseCase;

  @Autowired
  private AddNewClassificationUseCase addNewClassificationUseCase;

  @Autowired
  private AddImageUseCase addImageUseCase;

  @PostMapping(value = "/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> createVideoGame(@RequestBody CreateVideoGameInput input) {
    return new ResponseEntity<>(mapper
        .toDto(createUseCase.createVideoGame(mapper
            .fromDtoToDomain(mapper
                .fromInputToDto(input)))), HttpStatus.CREATED);
  }

  @PostMapping(value = "/{id}/image")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> addCover(@RequestPart(name="image") MultipartFile image, @PathVariable UUID id) throws IOException {
    return new ResponseEntity<>(mapper
            .toDto(addImageUseCase.addImage(image, id)), HttpStatus.CREATED);
  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<VideoGameDTO> getOneVideogameById(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneVideoGame(id)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El videojuego a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
    }
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> getAllVideogames() {
    List<VideoGame> list = findAllUseCase.findAllVideoGames();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<VideoGameDTO> updateVideoGame(@RequestBody CreateVideoGameInput input) {
    try {
      return new ResponseEntity<>(mapper
          .toDto(updateUseCase.updateVideoGame(mapper
              .fromDtoToDomain(mapper
                  .fromUpdateInputToDto(input)))), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(
          "El videojuego a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<VideoGameDTO> deleteVideogame(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteVideoGame(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El videojuego a eliminar no existe. Prueba con otro");
    }
  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<VideoGameDTO> getOneVideoGameByName(@PathVariable String name) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByNameUseCase.findByName(name)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El videojuego a buscar no se encuentra registrado en la base de datos. Prueba con otro nombre");
    }

  }

  @GetMapping("/developer/{developer}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByDeveloper(@PathVariable String developer){
    return new ResponseEntity<>(filterByDeveloperUseCase.videoGamesByDeveloper(developer.toString()).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @PostMapping("/platforms")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByPlatforms(@RequestBody List<String> platforms){
    return new ResponseEntity<>(filterByPlatformsUseCase.videoGamesByPlatforms(platforms).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/tag/{tag}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByTag(@PathVariable String tag){
    return new ResponseEntity<>(filterByTagUseCase.videoGamesByTag(tag).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/system/{system}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterBySystem(@PathVariable String system){
    return new ResponseEntity<>(filterBySystemUseCase.videoGamesBySystem(system).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @PutMapping("/classification/{videoGameName}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> addNewClassificationToExistingVideoGame(@RequestBody CreateClassificationInput input, @PathVariable String videoGameName){
    try{
      return new ResponseEntity<>(mapper
          .toDto(addNewClassificationUseCase.addClassification(classMapper.fromDtoToDomain(classMapper
              .fromInputToDto(input)), videoGameName)), HttpStatus.CREATED);
    } catch (NoSuchElementException e){
      throw new NoSuchElementException("El videojuego a actualizar no se encuentra registrado en la base de datos. Prueba con otro nombre");
    }
  }

  @GetMapping("/latest")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> getThreeLatestAddedVideogames(){
    return new ResponseEntity<>(latestVideogamesUseCase.threeLatestVideogames().stream()
            .map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }
}
