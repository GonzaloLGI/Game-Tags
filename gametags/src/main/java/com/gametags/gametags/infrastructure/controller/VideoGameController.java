package com.gametags.gametags.infrastructure.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.videogame.DeleteVideoGameUseCase;
import com.gametags.gametags.application.videogame.FindAllVideoGamesUseCase;
import com.gametags.gametags.application.videogame.FindVideoGameByIdUseCase;
import com.gametags.gametags.application.videogame.FindVideoGameByNameUseCase;
import com.gametags.gametags.application.videogame.UpdateVideoGameUseCase;
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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  private AddNewClassificationUseCase addNewClassificationUseCase;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> createVideoGame(@RequestBody CreateVideoGameInput input) {
    return new ResponseEntity<>(mapper
        .toDto(createUseCase.createVideoGame(mapper
            .fromDtoToDomain(mapper
                .fromInputToDto(input)))), HttpStatus.CREATED);
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

  @GetMapping("/developer")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByDeveloper(@RequestBody String developer){
    return new ResponseEntity<>(filterByDeveloperUseCase.videoGamesByDeveloper(developer.toString()).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/platforms")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByPlatforms(@RequestBody List<String> platforms){
    return new ResponseEntity<>(filterByPlatformsUseCase.videoGamesByPlatforms(platforms).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/tag")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterByTag(@RequestBody String tag){
    System.out.println("ARGUMENTO: " + tag.toString());
    return new ResponseEntity<>(filterByTagUseCase.videoGamesByTag(tag).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/system")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> filterBySystem(@RequestBody String system){
    System.out.println("ARGUMENTO: " + system.toString());
    return new ResponseEntity<>(filterBySystemUseCase.videoGamesBySystem(system).stream().map(videogame -> mapper.toDto(videogame)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @PutMapping("/classification/{videoGameName}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> addNewClassificationToExistingVideoGame(@RequestBody CreateClassificationInput input, @PathVariable String videoGameName){
    try{
      System.out.println("ARGUMENTO: " + videoGameName.toString());
      return new ResponseEntity<>(mapper
          .toDto(addNewClassificationUseCase.addClassification(classMapper.fromDtoToDomain(classMapper
              .fromInputToDto(input)), videoGameName)), HttpStatus.CREATED);
    } catch (NoSuchElementException e){
      throw new NoSuchElementException("El videojuego a actualizar no se encuentra registrado en la base de datos. Prueba con otro nombre");
    }
  }
}
