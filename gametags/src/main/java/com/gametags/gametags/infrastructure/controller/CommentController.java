package com.gametags.gametags.infrastructure.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.comment.create_comment.CreateCommentInput;
import com.gametags.gametags.application.comment.create_comment.CreateCommentUseCase;
import com.gametags.gametags.application.comment.DeleteCommentUseCase;
import com.gametags.gametags.application.comment.FindAllCommentsUseCase;
import com.gametags.gametags.application.comment.FindCommentByIdUseCase;
import com.gametags.gametags.application.comment.UpdateCommentUseCase;
import com.gametags.gametags.application.comment.filter_comments.FilterByCategoryUseCase;
import com.gametags.gametags.application.comment.filter_comments.FilterBySeverityUseCase;
import com.gametags.gametags.application.comment.filter_comments.FilterByVideoGameUseCase;
import com.gametags.gametags.application.comment.filter_comments.FilterByVideogameInput;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.mappers.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment/")
@Slf4j
public class CommentController {
  @Autowired
  private CommentMapper mapper;

  @Autowired
  private CreateCommentUseCase createUseCase;

  @Autowired
  private DeleteCommentUseCase deleteUseCase;

  @Autowired
  private FindAllCommentsUseCase findAllUseCase;

  @Autowired
  private FindCommentByIdUseCase findByIdUseCase;

  @Autowired
  private UpdateCommentUseCase updateUseCase;

  @Autowired
  private FilterByCategoryUseCase filterByCategoryUseCase;

  @Autowired
  private FilterBySeverityUseCase filterBySeverityUseCase;

  @Autowired
  private FilterByVideoGameUseCase filterByVideoGameUseCase;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CommentDTO> createComment(@RequestBody CreateCommentInput input) {
    return new ResponseEntity<>(mapper.toDto(createUseCase.createComment(mapper
            .fromDtoToDomain(mapper
                    .fromInputToDto(input)))), HttpStatus.CREATED);
  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<CommentDTO> getOneCommentById(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(findByIdUseCase.findOneComment(id)), HttpStatus.FOUND);
    } catch (NoSuchElementException e) {
      log.info("El comentario a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
      return new ResponseEntity<>(CommentDTO.builder().build(),HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    List<Comment> list = findAllUseCase.findAllComments();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).toList(), HttpStatus.FOUND);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<CommentDTO> updateComment(@RequestBody CreateCommentInput input) {
    try {
      return new ResponseEntity<>(mapper
          .toDto(updateUseCase.updateComment(mapper
              .fromDtoToDomain(mapper
                  .fromUpdateInputToDto(input)))), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      log.info("El comentario a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
      return new ResponseEntity<>(CommentDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<CommentDTO> deleteComment(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteComment(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      log.info("El usuario a eliminar no existe. Prueba con otro");
      return new ResponseEntity<>(CommentDTO.builder().build(),HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{userName}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> getAllCommentsOfUser(@PathVariable String userName) {
    List<Comment> list = findAllUseCase.findAllCommentsOfUser(userName);
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).toList(), HttpStatus.FOUND);
  }

  @PostMapping("/category/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByCategoryAndUser(@RequestBody String category){
    return new ResponseEntity<>(filterByCategoryUseCase.commentsByCategoryAndUser(category).stream().map(comment -> mapper.toDto(comment)).toList(), HttpStatus.FOUND);
  }

  @PostMapping("/severity/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterBySeverityAndUser(@RequestBody String severity){
    return new ResponseEntity<>(filterBySeverityUseCase.commentsBySeverityAndUser(severity).stream().map(comment -> mapper.toDto(comment)).toList(), HttpStatus.FOUND);
  }

  @PostMapping("/videogame/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByVideoGameAndUser(@RequestBody FilterByVideogameInput videogame){
    return new ResponseEntity<>(filterByVideoGameUseCase.commentsByVideoGameAndUser(mapper.fromFilterVideogameInputToString(videogame)).stream().map(comment -> mapper.toDto(comment)).toList(), HttpStatus.FOUND);
  }

  @GetMapping("/category/videogame/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByCategoryAndVideoGame(@PathVariable String videogameName, @RequestBody String category){
    return new ResponseEntity<>(filterByCategoryUseCase.commentsByCategoryAndVideoGame(category, videogameName).stream().map(comment -> mapper.toDto(comment)).toList(), HttpStatus.FOUND);
  }

  @GetMapping("/severity/videogame/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterBySeverityAndVideoGame(@PathVariable String videogameName, @RequestBody String severity){
    return new ResponseEntity<>(filterBySeverityUseCase.commentsBySeverityAndVideoGame(severity, videogameName).stream().map(comment -> mapper.toDto(comment)).toList(), HttpStatus.FOUND);
  }
}
