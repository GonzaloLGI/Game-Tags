package com.gametags.gametags.infrastructure.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.user.*;
import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.application.user.create_user.CreateUserUseCase;
import com.gametags.gametags.application.user.update_password.UpdatePasswordInput;
import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.application.user.update_username.UpdateUsernameInput;
import com.gametags.gametags.application.user.update_username.UpdateUsernameUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/user/")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

  @Autowired
  private UserMapper mapper;

  @Autowired
  private CreateUserUseCase createUseCase;

  @Autowired
  private DeleteUserUseCase deleteUseCase;

  @Autowired
  private FindAllUsersUseCase findAllUseCase;

  @Autowired
  private FindUserByIdUseCase findByIdUseCase;

  @Autowired
  private FindUserByNameUseCase findByUsernameUseCase;

  @Autowired
  private UpdateUserUseCase updateUseCase;

  @Autowired
  private UpdateUsernameUseCase updateUsernameUseCase;

  @Autowired
  private UpdatePasswordUseCase updatePasswordUseCase;

  @Autowired
  private AddProfileImageUseCase addProfileImageUseCase;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserInput input) {
    return new ResponseEntity<>(mapper
        .toDto(createUseCase.createUser(mapper
            .fromDtoToDomain(mapper
                .fromInputToDto(input)))), HttpStatus.CREATED);
  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<UserDTO> getOneUserById(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneUser(id)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      log.info("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
      return new ResponseEntity<>(UserDTO.builder().build(),HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<UserDTO> getOneUserByName(@PathVariable String name) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByUsernameUseCase.findOneUser(name)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      log.info("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro nombre");
      return new ResponseEntity<>(UserDTO.builder().build(),HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> list = findAllUseCase.findAllUsers();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).toList(), HttpStatus.FOUND);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<UserDTO> updateUser(@RequestBody CreateUserInput input) {
    try {
      return new ResponseEntity<>(mapper
          .toDto(updateUseCase.updateUser(mapper
              .fromDtoToDomain(mapper
                  .fromUpdateInputToDto(input)))), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      log.info("El usuario a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
      return new ResponseEntity<>(UserDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteUser(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      log.info("El usuario a eliminar no existe. Prueba con otro");
      return new ResponseEntity<>(UserDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/username")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<AuthResponseDTO> updateUsername(@RequestBody UpdateUsernameInput input, Principal principal){
    try{
      return new ResponseEntity<>(updateUsernameUseCase.updateUsername(input.getNewUsername(), input.getExistingPassword(), principal), HttpStatus.ACCEPTED);
    } catch (NotFoundException e){
      log.info("La contraseña introducida no coincide con la almacenada. No se puede actualizar");
      return new ResponseEntity<>(new AuthResponseDTO("",""),HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/password")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<AuthResponseDTO> updatePassword(@RequestBody UpdatePasswordInput input){
    try{
      return new ResponseEntity<>(updatePasswordUseCase.updatePassword(input), HttpStatus.ACCEPTED);
    } catch (NotFoundException e){
      log.info("La contraseña introducida no coincide con la almacenada. No se puede actualizar");
      return new ResponseEntity<>(new AuthResponseDTO("",""),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/{id}/image")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDTO> addProfileImage(@RequestPart(name="image") MultipartFile image, @PathVariable UUID id) throws IOException {
    return new ResponseEntity<>(mapper
            .toDto(addProfileImageUseCase.addImage(image, id)), HttpStatus.CREATED);
  }
}
