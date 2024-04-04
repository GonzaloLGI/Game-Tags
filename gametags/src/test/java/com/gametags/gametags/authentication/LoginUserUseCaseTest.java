package com.gametags.gametags.authentication;

import com.gametags.gametags.application.authentication.LoginUserUseCase;
import com.gametags.gametags.application.authentication.RegisterUserUseCase;
import com.gametags.gametags.domain.model.LoginInput;
import com.gametags.gametags.domain.model.RegisterInput;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.mappers.AuthenticationMapper;
import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {

	@InjectMocks
	LoginUserUseCase useCase;

	@Mock
	UserService service;

	@Mock
	Authentication authentication;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JWTGenerator jwtGenerator;

	@Test
	public void userDoesntExist(){
		//GIVEN
		LoginInput input = LoginInput.builder()
				.password("password")
				.userName("username")
				.build();
		when(service.findOneUserByUsername(any())).thenReturn(null);

		//WHEN
		Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.loginUser(input));

		//THEN
		assertEquals("The user doesn't exist",exception.getMessage());

	}

	@Test
	public void loginUser() throws AlreadyUsedException {
		//GIVEN
		User user = User.builder()
				.id(UUID.randomUUID())
				.password("password")
				.username("username")
				.country("country")
				.email("country")
				.roles(List.of("ROLE_USER"))
				.build();
		LoginInput input = LoginInput.builder()
				.password("password")
				.userName("username")
				.build();
		AuthResponseDTO authResponseDTO = new AuthResponseDTO("token","username");
		when(service.findOneUserByUsername(any())).thenReturn(user);
		when(authentication.getName()).thenReturn("username");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		when(jwtGenerator.generateToken(any())).thenReturn("token");
		when(authenticationManager.authenticate(any())).thenReturn(authentication);

		//WHEN
		AuthResponseDTO response = useCase.loginUser(input);

		//THEN
		assertEquals(authResponseDTO.getAccessToken(),response.getAccessToken());
		assertEquals(authResponseDTO.getUsername(),response.getUsername());
	}

}
