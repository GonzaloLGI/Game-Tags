package com.gametags.gametags.videogame;

import com.gametags.gametags.application.videogame.AddImageUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddImageUseCaseTest {

	@InjectMocks
	AddImageUseCase useCase;

	@Mock
	VideoGameService service;

	MultipartFile image;

	@BeforeEach
	public void initialize(){
		image = mock(MultipartFile.class);
	}

	@Test
	public void addImageCoverToVideoGame() throws IOException {
		//GIVEN
		UUID videoGameId = UUID.randomUUID();
		VideoGame existingVideoGame = VideoGame.builder()
				.id(videoGameId)
				.name("name")
				.developer("developer")
				.platforms(List.of("platform1", "platform2"))
				.uploadDateTime(LocalDateTime.now())
				.classifications(new ArrayList<>(List.of(Classification.builder().build())))
				.build();

		when(service.findOneVideoGame(any(UUID.class))).thenReturn(existingVideoGame);
		when(service.updateVideoGame(any())).thenReturn(existingVideoGame);
		when(image.getOriginalFilename()).thenReturn("fileName");
		when(image.getBytes()).thenReturn(HexFormat.of().parseHex(""));

		//WHEN
		VideoGame response = useCase.addImage(image,videoGameId);

		//THEN
		assertEquals(existingVideoGame.getId(), response.getId());
		assertNotNull(response.getImageData());


	}

	@Test
	public void cantAddImageCoverToVideoGameBecauseVideoGameDoesntExist() throws IOException {
		//GIVEN
		UUID userId = UUID.randomUUID();

		when(service.findOneVideoGame(any(UUID.class))).thenReturn(VideoGame.builder().build());

		//WHEN
		VideoGame response = useCase.addImage(image,userId);

		//THEN
		assertEquals(null, response.getId());

	}

}
