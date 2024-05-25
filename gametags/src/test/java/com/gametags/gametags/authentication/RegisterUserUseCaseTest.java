package com.gametags.gametags.authentication;

import com.gametags.gametags.application.authentication.RegisterUserUseCase;
import com.gametags.gametags.domain.model.RegisterInput;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {

	@InjectMocks
	RegisterUserUseCase useCase;

	@Mock
	UserService service;

	@Test
	public void cantRegisterExistingUser(){
		//GIVEN
		User existingUser = User.builder()
				.id(UUID.randomUUID())
				.password("password")
				.username("username")
				.country("country")
				.email("country")
				.roles(List.of("ROLE_USER"))
				.build();
		RegisterInput input = RegisterInput.builder()
				.password("password")
				.userName("username")
				.country("country")
				.email("email")
				.build();
		when(service.findOneUserByUsername(any())).thenReturn(existingUser);

		//WHEN
		Exception exception = assertThrows(AlreadyUsedException.class, () -> useCase.registerUser(input));

		//THEN
		assertEquals("User already existing",exception.getMessage());

	}

	@Test
	public void registerNewUser() throws AlreadyUsedException {
		//GIVEN
		User user = User.builder()
				.id(UUID.randomUUID())
				.password("password")
				.username("username")
				.country("country")
				.email("country")
				.roles(List.of("ROLE_USER"))
				.build();
		RegisterInput input = RegisterInput.builder()
				.password("password")
				.userName("username")
				.country("country")
				.email("email")
				.build();
		when(service.findOneUserByUsername(any())).thenReturn(User.builder().build());
		when(service.createUser(any())).thenReturn(user);

		//WHEN
		User createdUser = useCase.registerUser(input);

		//THEN
		assertEquals(user.getId(),createdUser.getId());
	}

}
