package com.gametags.gametags.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
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
@RequestMapping("/videogame/")
public class VideoGameController {

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<VideoGameDTO> createVideoGame(@RequestBody CreateUserInput input) {

  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<VideoGameDTO> getOneVideogameById(@PathVariable UUID id) {

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<VideoGameDTO>> getAllVideogames() {
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<VideoGameDTO> updateVideogame(@RequestBody CreateUserInput input) {

  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<VideoGameDTO> deleteVideogame(@PathVariable UUID id) {

  }
}
