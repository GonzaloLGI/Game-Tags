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
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.mappers.CommentMapper;
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
@RequestMapping("/comment/")
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
      throw new NoSuchElementException("El comentario a buscar no se encuentra registrado en la base de datos. Prueba con otro Id");
    }

  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    List<Comment> list = findAllUseCase.findAllComments();
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()), HttpStatus.FOUND);
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
      throw new NoSuchElementException(
          "El comentario a actualizar no se encuentra registrado en la base de datos. Prueba con otro o registra el actual");
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<CommentDTO> deleteComment(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(mapper.toDto(deleteUseCase.deleteComment(id)), HttpStatus.ACCEPTED);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("El usuario a eliminar no existe. Prueba con otro");
    }
  }

  //AÑADIR ENDPOINT DE SOLICITUD DE COMENTARIOS POR USUARIO
  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> getAllCommentsOfUser(@PathVariable UUID userId) {
    List<Comment> list = findAllUseCase.findAllCommentsOfUser(userId);
    return new ResponseEntity<>(list.stream().map(dao -> mapper.toDto(dao)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/category/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByCategoryAndUser(@RequestBody String category){
    return new ResponseEntity<>(filterByCategoryUseCase.commentsByCategoryAndUser(category.toString()).stream().map(comment -> mapper.toDto(comment)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/severity/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterBySeverityAndUser(@RequestBody String severity){
    return new ResponseEntity<>(filterBySeverityUseCase.commentsBySeverityAndUser(severity.toString()).stream().map(comment -> mapper.toDto(comment)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/videogame/user")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByVideoGameAndUser(@RequestBody UUID videogame){
    return new ResponseEntity<>(filterByVideoGameUseCase.commentsByVideoGameAndUser(videogame).stream().map(comment -> mapper.toDto(comment)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/category/videogame/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterByCategoryAndVideoGame(@PathVariable UUID id, @RequestBody String category){
    return new ResponseEntity<>(filterByCategoryUseCase.commentsByCategoryAndVideoGame(category.toString(), id).stream().map(comment -> mapper.toDto(comment)).collect(Collectors.toList()), HttpStatus.FOUND);
  }

  @GetMapping("/severity/videogame/{id}")
  @ResponseStatus(HttpStatus.FOUND)
  public ResponseEntity<List<CommentDTO>> filterBySeverityAndVideoGame(@PathVariable UUID id, @RequestBody String severity){
    return new ResponseEntity<>(filterBySeverityUseCase.commentsBySeverityAndVideoGame(severity.toString(), id).stream().map(comment -> mapper.toDto(comment)).collect(Collectors.toList()), HttpStatus.FOUND);
  }
}
