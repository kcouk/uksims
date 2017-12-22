package co.uk.kuwait.userservice.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

// import co.uk.kuwait.userservice.validation.PasswordMatches;
// import co.uk.kuwait.userservice.validation.ValidEmail;
// import co.uk.kuwait.userservice.validation.ValidPassword;

// @PasswordMatches
@Getter
@Setter
public class UserDto {
	@NotNull
	@Size(min = 1)
	private String firstName;

	@NotNull
	@Size(min = 1)
	private String lastName;

	// @ValidPassword
	private String password;

	@NotNull
	@Size(min = 1)
	private String matchingPassword;

	// @ValidEmail
	@NotNull
	@Size(min = 1)
	private String email;

	@NotNull
	@Size(min = 1)
	private String username;


	// private boolean isUsing2FA;


	private Integer role;

}
