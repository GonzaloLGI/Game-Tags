package com.gametags.gametags.infrastructure.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.user.DeleteUserUseCase;
import com.gametags.gametags.application.user.FindAllUsersUseCase;
import com.gametags.gametags.application.user.FindUserByIdUseCase;
import com.gametags.gametags.application.user.FindUserByNameUseCase;
import com.gametags.gametags.application.user.UpdateUserUseCase;
import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.application.user.create_user.CreateUserUseCase;
import com.gametags.gametags.application.user.update_password.UpdatePasswordInput;
import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.application.user.update_username.UpdateUsernameInput;
import com.gametags.gametags.application.user.update_username.UpdateUsernameUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
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
@RequestMapping("/user/")
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
      throw new NoSuchElementException("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
    }

  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<UserDTO> getOneUserByName(@PathVariable String name) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByUsernameUseCase.findOneUser(name)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro nombre");
    }

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> list = findAllUseCase.findAllUsers();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()), HttpStatus.FOUND);
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
      throw new NoSuchElementException(
          "El usuario a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteUser(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El usuario a eliminar no existe. Prueba con otro");
    }
  }

  @PutMapping("/username")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<AuthResponseDTO> updateUsername(@RequestBody UpdateUsernameInput input, Principal principal){
    return new ResponseEntity<>(updateUsernameUseCase.updateUsername(input.getNewUsername(), input.getExistingPassword(), principal), HttpStatus.ACCEPTED);
  }

  @PutMapping("/password")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<AuthResponseDTO> updatePassword(@RequestBody UpdatePasswordInput input){
    return new ResponseEntity<>(updatePasswordUseCase.updatePassword(input), HttpStatus.ACCEPTED);
  }
}
