package com.gametags.gametags.videogame;

import com.gametags.gametags.application.videogame.add_new_classification.AddNewClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddNewClassificationToExistingVideoGameUseCaseTest {

  @Mock
  VideoGameService videoGameService;

  @Mock
  ClassificationService classificationService;

  @InjectMocks
  AddNewClassificationUseCase addNewClassificationUseCase;

  @Test
  public void addNewClassificationToExistingGame() {
    //GIVEN
    String videoGameName = "videoGame";
    Classification newClassification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("newTag")
            .url("newUrl")
            .country("newCountry")
            .system("newSystem")
            .build();
    VideoGame updatedVideogame = VideoGame.builder()
            .id(UUID.randomUUID())
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .classifications(new ArrayList<>(List.of(newClassification)))
            .build();
    when(videoGameService.findVideoGameByName(any(String.class))).thenReturn(VideoGame.builder().id(UUID.randomUUID()).classifications(new ArrayList<>()).build());
    when(classificationService.findOneClassificationBySystemAndTag(any(String.class),any(String.class))).thenReturn(Classification.builder().build());
    when(classificationService.createClassification(any())).thenReturn(newClassification);
    when(videoGameService.updateVideoGame(any())).thenReturn(updatedVideogame);

    //WHEN
    VideoGame returned = addNewClassificationUseCase.addClassification(newClassification,videoGameName);

    //THEN
    assertEquals(1,returned.getClassifications().size());
    assertEquals(newClassification.getId(),returned.getClassifications().get(0).getId());

  }

  @Test
  public void addAlreadyAddedClassificationToExistingGame() {
    //GIVEN
    String videoGameName = "videoGame";
    Classification newClassification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("newTag")
            .url("newUrl")
            .country("newCountry")
            .system("newSystem")
            .build();
    VideoGame updatedVideogame = VideoGame.builder()
            .id(UUID.randomUUID())
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .classifications(new ArrayList<>(List.of(newClassification)))
            .build();
    when(videoGameService.findVideoGameByName(any(String.class))).thenReturn(VideoGame.builder().id(UUID.randomUUID()).classifications(new ArrayList<>(List.of(newClassification))).build());
    when(classificationService.findOneClassificationBySystemAndTag(any(String.class),any(String.class))).thenReturn(newClassification);
    when(videoGameService.updateVideoGame(any())).thenReturn(updatedVideogame);

    //WHEN
    VideoGame returned = addNewClassificationUseCase.addClassification(newClassification,videoGameName);

    //THEN
    assertEquals(1,returned.getClassifications().size());
    assertEquals(newClassification.getId(),returned.getClassifications().get(0).getId());

  }

  @Test
  public void addExistingClassificationNotYetAddedToExistingGame() {
    //GIVEN
    String videoGameName = "videoGame";
    Classification existingClassification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("newTag")
            .url("newUrl")
            .country("newCountry")
            .system("newSystem")
            .build();
    VideoGame updatedVideogame = VideoGame.builder()
            .id(UUID.randomUUID())
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .classifications(new ArrayList<>(List.of(existingClassification)))
            .build();
    when(videoGameService.findVideoGameByName(any(String.class))).thenReturn(VideoGame.builder().id(UUID.randomUUID()).classifications(new ArrayList<>()).build());
    when(classificationService.findOneClassificationBySystemAndTag(any(String.class),any(String.class))).thenReturn(existingClassification);
    when(videoGameService.updateVideoGame(any())).thenReturn(updatedVideogame);

    //WHEN
    VideoGame returned = addNewClassificationUseCase.addClassification(existingClassification,videoGameName);

    //THEN
    assertEquals(1,returned.getClassifications().size());
    assertEquals(existingClassification.getId(),returned.getClassifications().get(0).getId());

  }

  @Test
  public void cantAddClassificationBecauseVideoGameDoesntExist() {
    //GIVEN
    String videoGameName = "videoGame";
    Classification newClassification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("newTag")
            .url("newUrl")
            .country("newCountry")
            .system("newSystem")
            .build();
    when(videoGameService.findVideoGameByName(any(String.class))).thenReturn(VideoGame.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> addNewClassificationUseCase.addClassification(newClassification,videoGameName));

    //THEN
    assertEquals("The videogame is not registered",exception.getMessage());

  }

}
