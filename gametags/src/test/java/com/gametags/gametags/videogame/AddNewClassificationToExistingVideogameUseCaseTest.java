package com.gametags.gametags.videogame;

import com.gametags.gametags.application.videogame.add_new_classification.AddNewClassificationUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddNewClassificationToExistingVideogameUseCaseTest {

  @Mock
  VideoGameService service;

  @InjectMocks
  AddNewClassificationUseCase addNewClassificationUseCase;

  @Test
  public void addNewClassificationToExistingGame() {
    //GIVEN
    //WHEN
    //VideoGame returned = addNewClassificationUseCase.addClassification();
    //THEN

  }

}
