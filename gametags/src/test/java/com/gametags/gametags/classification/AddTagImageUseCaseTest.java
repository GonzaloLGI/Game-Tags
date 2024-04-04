package com.gametags.gametags.classification;

import com.gametags.gametags.application.classification.AddTagImageUseCase;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.VideoGameService;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddTagImageUseCaseTest {

	@InjectMocks
	AddTagImageUseCase useCase;

	@Mock
	ClassificationService classificationService;

	@Mock
	VideoGameService videoGameService;

	MultipartFile image;

	@BeforeEach
	public void initialize(){
		image = mock(MultipartFile.class);
	}

	@Test
	public void cantAddTagBecauseClassificationDoesntExist() throws IOException {
		//GIVEN
		UUID classificationId = UUID.randomUUID();
		UUID videoGameId = UUID.randomUUID();
		when(classificationService.findOneClassification(any())).thenReturn(Classification.builder().build());

		//WHEN
		Classification response = useCase.addImage(image,classificationId,videoGameId);

		//THEN
		assertEquals(null, response.getId());
	}

	@Test
	public void cantAddTagBecauseVideoGameDoesntExist() throws IOException {
		//GIVEN
		UUID classificationId = UUID.randomUUID();
		UUID videoGameId = UUID.randomUUID();
		Classification classification = Classification.builder()
				.id(classificationId)
				.tag("tag")
				.url("url")
				.country("country")
				.system("system")
				.build();
		when(classificationService.findOneClassification(any())).thenReturn(classification);
		when(image.getOriginalFilename()).thenReturn("fileName");
		when(image.getBytes()).thenReturn(HexFormat.of().parseHex(""));
		when(videoGameService.findOneVideoGame(any(UUID.class))).thenReturn(VideoGame.builder().build());

		//WHEN
		Classification response = useCase.addImage(image,classificationId,videoGameId);

		//THEN
		assertEquals(classification.getId(), response.getId());
	}

	@Test
	public void addTagImageToVideoGame() throws IOException {
		//GIVEN
		UUID classificationId = UUID.randomUUID();
		UUID videoGameId = UUID.randomUUID();
		Classification classification = Classification.builder()
				.id(classificationId)
				.tag("tag")
				.url("url")
				.country("country")
				.system("system")
				.build();
		Classification updatedClassification = Classification.builder()
				.id(classificationId)
				.tag("tag")
				.url("url")
				.country("country")
				.system("system")
				.imageData(new Binary(HexFormat.of().parseHex("")))
				.build();
		VideoGame videogame = VideoGame.builder()
				.id(UUID.randomUUID())
				.name("name")
				.developer("developer")
				.platforms(List.of("platform1", "platform2"))
				.uploadDateTime(LocalDateTime.now())
				.classifications(new ArrayList<>(List.of(classification)))
				.build();
		VideoGame updatedVideogame = VideoGame.builder()
				.id(UUID.randomUUID())
				.name("name")
				.developer("developer")
				.platforms(List.of("platform1", "platform2"))
				.uploadDateTime(LocalDateTime.now())
				.classifications(new ArrayList<>(List.of(updatedClassification)))
				.build();
		when(classificationService.findOneClassification(any())).thenReturn(classification);
		when(image.getOriginalFilename()).thenReturn("fileName");
		when(image.getBytes()).thenReturn(HexFormat.of().parseHex(""));
		when(videoGameService.findOneVideoGame(any(UUID.class))).thenReturn(videogame);
		when(classificationService.updateClassification(any())).thenReturn(updatedClassification);
		when(videoGameService.updateVideoGame(any())).thenReturn(updatedVideogame);

		//WHEN
		Classification response = useCase.addImage(image,classificationId,videoGameId);

		//THEN
		assertNotNull(response.getImageData());
	}

}
