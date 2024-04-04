package com.gametags.gametags.authentication;

import com.gametags.gametags.application.authentication.LoginDTO;
import com.gametags.gametags.application.authentication.RegisterDTO;
import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.domain.model.LoginInput;
import com.gametags.gametags.domain.model.RegisterInput;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.mappers.AuthenticationMapper;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationMapperTest {

	@InjectMocks
	AuthenticationMapper mapper;

	@Mock
	PasswordEncoder passwordEncoder;

	@Test
	public void mappingFromRegisterDtoToInput() {
		//GIVEN
		RegisterDTO dto = RegisterDTO.builder()
				.username("username")
				.password("password")
				.country("country")
				.email("email")
				.build();
		when(passwordEncoder.encode(any())).thenReturn("encodedpassword2");

		//WHEN
		RegisterInput mapped = mapper.fromRegisterDtoToInput(dto);

		//THEN
		assertEquals(mapped.getUserName(), "username");
		assertNotEquals(mapped.getPassword(), "password");
		assertEquals(mapped.getEmail(), "email");
		assertEquals(mapped.getCountry(), "country");
	}

	@Test
	public void mappingFromLoginDtoToInput() {
		//GIVEN
		LoginDTO dto = LoginDTO.builder()
				.username("username")
				.password("password")
				.build();

		//WHEN
		LoginInput mapped = mapper.fromLoginDtoToInput(dto);

		//THEN
		assertEquals(mapped.getUserName(),"username");
		assertEquals(mapped.getPassword(),"password");
	}
}
