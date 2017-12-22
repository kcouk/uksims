package co.uk.kuwait.userservice.service;

import java.util.List;

import co.uk.kuwait.userservice.entity.PasswordResetToken;
import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.entity.VerificationToken;
import co.uk.kuwait.userservice.web.dto.UserDto;
import co.uk.kuwait.userservice.web.error.UserAlreadyExistException;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

	User getUser(String verificationToken);

	void saveRegisteredUser(User user);

	void deleteUser(User user);

	void createVerificationTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	VerificationToken generateNewVerificationToken(String token);

	void createPasswordResetTokenForUser(User user, String token);

	User findUserByEmail(String email);

	PasswordResetToken getPasswordResetToken(String token);

	User getUserByPasswordResetToken(String token);

	User getUserByID(long id);

	void changeUserPassword(User user, String password);

	boolean checkIfValidOldPassword(User user, String password);

	String validateVerificationToken(String token);

	// String generateQRUrl(User user) throws UnsupportedEncodingException;
	//
	// User updateUser2FA(boolean use2FA);

	List<String> getUsersFromSessionRegistry();

}
