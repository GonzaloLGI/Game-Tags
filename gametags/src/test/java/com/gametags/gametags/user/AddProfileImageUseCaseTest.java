package com.gametags.gametags.user;

import com.gametags.gametags.application.user.AddProfileImageUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HexFormat;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddProfileImageUseCaseTest {

	@InjectMocks
	AddProfileImageUseCase useCase;

	@Mock
	UserService service;

	MultipartFile image;

	@BeforeEach
	public void initialize(){
		image = mock(MultipartFile.class);
	}

	@Test
	public void addProfileImageToUser() throws IOException {
		//GIVEN
		UUID userId = UUID.randomUUID();
		User existingUser = User.builder()
				.id(userId)
				.username("username")
				.email("email")
				.password("password")
				.country("country")
				.build();

		when(service.findOneUserById(any(UUID.class))).thenReturn(existingUser);
		when(service.updateUser(any())).thenReturn(existingUser);
		when(image.getOriginalFilename()).thenReturn("fileName");
		when(image.getBytes()).thenReturn(HexFormat.of().parseHex(""));

		//WHEN
		User response = useCase.addImage(image,userId);

		//THEN
		assertEquals(existingUser.getId(), response.getId());
		assertNotNull(response.getProfileImageData());


	}

	@Test
	public void cantAddProfileImageToUserBecauseUserDoesntExist() throws IOException {
		//GIVEN
		UUID userId = UUID.randomUUID();

		when(service.findOneUserById(any(UUID.class))).thenReturn(User.builder().build());

		//WHEN
		User response = useCase.addImage(image,userId);

		//THEN
		assertEquals(null, response.getId());

	}

}
