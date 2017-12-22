package co.uk.kuwait.userservice.web.dto;

import lombok.Getter;
import lombok.Setter;

// import co.uk.kuwait.userservice.validation.ValidPassword;

@Getter
@Setter
public class PasswordDto {

	private String oldPassword;

	// @ValidPassword
	private String newPassword;



}
