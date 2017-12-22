package co.uk.kuwait.userservice.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import co.uk.kuwait.userservice.entity.PasswordResetToken;
import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.entity.VerificationToken;
import co.uk.kuwait.userservice.repository.AuthorityRepository;
import co.uk.kuwait.userservice.repository.PasswordResetTokenRepository;
import co.uk.kuwait.userservice.repository.UserRepository;
import co.uk.kuwait.userservice.repository.VerificationTokenRepository;
import co.uk.kuwait.userservice.web.dto.UserDto;
import co.uk.kuwait.userservice.web.error.UserAlreadyExistException;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private SessionRegistry sessionRegistry;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	// public static String QR_PREFIX =
	// "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	// public static String APP_NAME = "SpringRegistration";

	// API

	@Override
	public User registerNewUserAccount(final UserDto accountDto) {
		if (this.emailExist(accountDto.getEmail()))
			throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());

		final User user = new User();

		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setPassword(this.passwordEncoder.encodePassword(accountDto.getPassword(), null));
		user.setEmail(accountDto.getEmail());
		user.setUsername(accountDto.getUsername());
		// user.setUsing2FA(accountDto.isUsing2FA());
		user.addAuthority(this.authorityRepository.findByAuthority("ROLE_USER"));

		return this.repository.save(user);
	}

	@Override
	public User getUser(final String verificationToken) {
		final VerificationToken token = this.tokenRepository.findByToken(verificationToken);
		if (token != null)
			return token.getUser();
		return null;
	}

	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		return this.tokenRepository.findByToken(VerificationToken);
	}

	@Override
	public void saveRegisteredUser(final User user) {
		this.repository.save(user);
	}

	@Override
	public void deleteUser(final User user) {
		final VerificationToken verificationToken = this.tokenRepository.findByUser(user);

		if (verificationToken != null)
			this.tokenRepository.delete(verificationToken);

		final PasswordResetToken passwordToken = this.passwordTokenRepository.findByUser(user);

		if (passwordToken != null)
			this.passwordTokenRepository.delete(passwordToken);

		this.repository.delete(user);
	}

	@Override
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		this.tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = this.tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = this.tokenRepository.save(vToken);
		return vToken;
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		this.passwordTokenRepository.save(myToken);
	}

	@Override
	public User findUserByEmail(final String email) {
		return this.repository.findByEmail(email);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return this.passwordTokenRepository.findByToken(token);
	}

	@Override
	public User getUserByPasswordResetToken(final String token) {
		return this.passwordTokenRepository.findByToken(token).getUser();
	}

	@Override
	public User getUserByID(final long id) {
		return this.repository.findOne(id);
	}

	@Override
	public void changeUserPassword(final User user, final String password) {
		user.setPassword(this.passwordEncoder.encodePassword(password, null));
		this.repository.save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
		return this.passwordEncoder.isPasswordValid(user.getPassword(), oldPassword, null);
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = this.tokenRepository.findByToken(token);
		if (verificationToken == null)
			return TOKEN_INVALID;

		final User user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
			this.tokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		user.setEnabled(true);
		// tokenRepository.delete(verificationToken);
		this.repository.save(user);
		return TOKEN_VALID;
	}

	// @Override
	// public String generateQRUrl(User user) throws UnsupportedEncodingException {
	// return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
	// APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
	// }
	//
	// @Override
	// public User updateUser2FA(boolean use2FA) {
	// final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
	// User currentUser = (User) curAuth.getPrincipal();
	// currentUser.setUsing2FA(use2FA);
	// currentUser = repository.save(currentUser);
	// final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser,
	// currentUser.getPassword(), curAuth.getAuthorities());
	// SecurityContextHolder.getContext().setAuthentication(auth);
	// return currentUser;
	// }

	private boolean emailExist(final String email) {
		return this.repository.findByEmail(email) != null;
	}

	@Override
	public List<String> getUsersFromSessionRegistry() {
		return this.sessionRegistry.getAllPrincipals().stream().filter((u) -> !this.sessionRegistry.getAllSessions(u, false).isEmpty())
				.map(Object::toString).collect(Collectors.toList());
	}

}
