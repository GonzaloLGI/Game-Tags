package com.gametags.gametags.infrastructure.mappers;

import java.util.UUID;

import com.gametags.gametags.application.comment.create_comment.CreateCommentInput;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.infrastructure.daos.CommentDAO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class CommentMapper {

  public CommentDTO fromInputToDto(CreateCommentInput input) {
    return CommentDTO.builder()
        .id(UUID.randomUUID())
        .text(input.getText())
        .category(input.getCategory())
        .severity(input.getSeverity())
        .uploadDate(input.getUploadDate())
        .build();
  }

  public Comment fromDtoToDomain(CommentDTO dto) {
    return Comment.builder()
        .id(dto.getId())
        .text(dto.getText())
        .category(dto.getCategory())
        .severity(dto.getSeverity())
        .uploadDate(dto.getUploadDate())
        .build();
  }

  public CommentDTO fromUpdateInputToDto(CreateCommentInput input) {
    return CommentDTO.builder()
        .id(input.getId())
        .text(input.getText())
        .category(input.getCategory())
        .severity(input.getSeverity())
        .uploadDate(input.getUploadDate())
        .build();
  }

  public CommentDTO toDto(Comment comment) {
    return CommentDTO.builder()
        .id(comment.getId())
        .text(comment.getText())
        .category(comment.getCategory())
        .severity(comment.getSeverity())
        .uploadDate(comment.getUploadDate())
        .build();
  }

  public CommentDAO toEntity(Comment comment) {
    return CommentDAO.builder()
        .id(comment.getId())
        .text(comment.getText())
        .category(comment.getCategory())
        .severity(comment.getSeverity())
        .uploadDate(comment.getUploadDate())
        .build();
  }

  public Comment fromEntityToDomain(CommentDAO dao) {
    return Comment.builder()
        .id(dao.getId())
        .text(dao.getText())
        .category(dao.getCategory())
        .severity(dao.getSeverity())
        .uploadDate(dao.getUploadDate())
        .build();
  }
}
