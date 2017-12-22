package co.uk.kuwait.userservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.service.IUserService;
import co.uk.kuwait.userservice.web.dto.PasswordDto;
import co.uk.kuwait.userservice.web.dto.UserDto;

@RestController
public class UserApiGateway {


	@Autowired
	private IUserService userService;


	@PostMapping("registerNewUserAccount")
	public User registerNewUserAccount(@RequestBody UserDto accountDto) {
		return this.userService.registerNewUserAccount(accountDto);
	}

	@PostMapping("changePassword")
	public void changePassword(@RequestBody PasswordDto passDTO) {
		final User user = this.userService.findUserByEmail("email@test.com");

		this.userService.changeUserPassword(user, passDTO.getNewPassword());
	}



}
