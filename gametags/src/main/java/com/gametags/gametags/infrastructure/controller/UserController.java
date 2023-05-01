package com.gametags.gametags.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserInput input){
        return new ResponseEntity<>(mapper
                .toDto(createUseCase.createUser(mapper
                        .fromDtoToDomain(mapper
                                .fromInputToDto(input)))),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<UserDTO> getOneUserById(@PathVariable UUID id){
        try{
            return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneUser(id)),HttpStatus.FOUND);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
        }

    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<UserDTO> getOneUserByName(@PathVariable String name){
        try{
            return new ResponseEntity<>(mapper.toDto(findByUsernameUseCase.findOneUser(name)),HttpStatus.FOUND);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("El usuario a buscar no se encuentra registrado en la base de datos. Prueba con otro nombre");
        }

    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> list = findAllUseCase.findAllUsers();
        return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()),HttpStatus.FOUND);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserDTO> updateUser(@RequestBody CreateUserInput input){
        try{
            return new ResponseEntity<>(mapper
                    .toDto(updateUseCase.updateUser(mapper
                            .fromDtoToDomain(mapper
                                    .fromCreateInputToDto(input)))),HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("El usuario a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id){
        try{
            return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteUser(id)),HttpStatus.ACCEPTED);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("El usuario a eliminar no existe. Prueba con otro");
        }
    }
}
