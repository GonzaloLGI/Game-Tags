package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.videogame.UpdateVideoGameUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateVideoGameUseCaseTest {

  @InjectMocks
  UpdateVideoGameUseCase useCase;

  @Mock
  VideoGameService service;

  @Mock
  CommentService commentService;

  @Test
  public void updateVideoGame() {
    //GIVEN
    VideoGame oldVideogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("developer1")
        .platforms(List.of("platform11", "platform12"))
        .uploadDateTime(LocalDateTime.now())
            .comments(new ArrayList<>(List.of(Comment.builder().build())))
        .build();
    VideoGame newVideogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name2")
        .developer("developer2")
        .platforms(List.of("platform21", "platform22"))
        .uploadDateTime(LocalDateTime.now())
            .comments(new ArrayList<>(List.of(Comment.builder().build())))
        .build();
    when(service.updateVideoGame(newVideogame)).thenReturn(newVideogame);
    when(service.findOneVideoGame(any(UUID.class))).thenReturn(oldVideogame);

    //WHEN
    VideoGame returnedVideogame = useCase.updateVideoGame(newVideogame);

    //THEN
    assertNotEquals(oldVideogame, returnedVideogame);
    assertEquals(newVideogame, returnedVideogame);

  }

  @Test
  public void cantUpdateBecauseVideoGameDoesntExist() {
    //GIVEN
    VideoGame newVideogame = VideoGame.builder()
            .id(UUID.randomUUID())
            .name("name2")
            .developer("developer2")
            .platforms(List.of("platform21", "platform22"))
            .uploadDateTime(LocalDateTime.now())
            .comments(new ArrayList<>(List.of(Comment.builder().build())))
            .build();
    when(service.findOneVideoGame(any(UUID.class))).thenReturn(VideoGame.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.updateVideoGame(newVideogame));

    //THEN
    assertEquals("The videogame is not registered",exception.getMessage());

  }

}