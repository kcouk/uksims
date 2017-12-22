package co.uk.kuwait.userservice.api;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.registration.OnRegistrationCompleteEvent;
import co.uk.kuwait.userservice.service.IUserService;
import co.uk.kuwait.userservice.utils.GenericResponse;
import co.uk.kuwait.userservice.web.dto.UserDto;

@Controller
public class RegistrationController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserService userService;
	//
	// @Autowired
	// private ISecurityUserService securityUserService;

	@Autowired
	private MessageSource messages;
	//
	// @Autowired
	// private JavaMailSender mailSender;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	//
	// @Autowired
	// private Environment env;
	//
	// public RegistrationController() {
	// super();
	// }

	// Registration
	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse registerUserAccount(@Valid @RequestBody final UserDto accountDto, final HttpServletRequest request) {
		this.LOGGER.debug("Registering user account with information: {}", accountDto);


		final User registered = this.userService.registerNewUserAccount(accountDto);
		this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), this.getAppUrl(request)));
		return new GenericResponse("success");
	}

	//
	// @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	// public String confirmRegistration(final Locale locale, final Model model,
	// @RequestParam("token")
	// final String token)
	// throws UnsupportedEncodingException {
	// final String result = this.userService.validateVerificationToken(token);
	// if (result.equals("valid")) {
	// final User user = this.userService.getUser(token);
	// System.out.println(user);
	// if (user.isUsing2FA()) {
	// model.addAttribute("qr", this.userService.generateQRUrl(user));
	// return "redirect:/qrcode.html?lang=" + locale.getLanguage();
	// }
	// model.addAttribute("message", this.messages.getMessage("message.accountVerified", null,
	// locale));
	// return "redirect:/login?lang=" + locale.getLanguage();
	// }
	//
	// model.addAttribute("message", this.messages.getMessage("auth.message." + result, null,
	// locale));
	// model.addAttribute("expired", "expired".equals(result));
	// model.addAttribute("token", token);
	// return "redirect:/badUser.html?lang=" + locale.getLanguage();
	// }
	//
	// // user activation - verification
	//
	// @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
	// @ResponseBody
	// public GenericResponse resendRegistrationToken(final HttpServletRequest request,
	// @RequestParam("token") final String existingToken) {
	// final VerificationToken newToken =
	// this.userService.generateNewVerificationToken(existingToken);
	// final User user = this.userService.getUser(newToken.getToken());
	// this.mailSender.send(this.constructResendVerificationTokenEmail(this.getAppUrl(request),
	// request.getLocale(), newToken, user));
	// return new GenericResponse(this.messages.getMessage("message.resendToken", null,
	// request.getLocale()));
	// }
	//
	// // Reset password
	@RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
		final User user = this.userService.findUserByEmail(userEmail);
		if (user != null) {
			final String token = UUID.randomUUID().toString();
			this.userService.createPasswordResetTokenForUser(user, token);
			// this.mailSender.send(this.constructResetTokenEmail(this.getAppUrl(request),
			// request.getLocale(), token, user));
		}
		return new GenericResponse("success");
		// return new GenericResponse(this.messages.getMessage("message.resetPasswordEmail", null,
		// request.getLocale()));
	}

	// @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	// public String showChangePasswordPage(final Locale locale, final Model model,
	// @RequestParam("id")
	// final long id,
	// @RequestParam("token") final String token) {
	// final String result = this.securityUserService.validatePasswordResetToken(id, token);
	// if (result != null) {
	// model.addAttribute("message", this.messages.getMessage("auth.message." + result, null,
	// locale));
	// return "redirect:/login?lang=" + locale.getLanguage();
	// }
	// return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
	// }
	//
	// @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	// @ResponseBody
	// public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
	// final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// this.userService.changeUserPassword(user, passwordDto.getNewPassword());
	// return new GenericResponse(this.messages.getMessage("message.resetPasswordSuc", null, locale));
	// }
	//
	// // change user password
	// @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	// @ResponseBody
	// public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto)
	// {
	// final User user = this.userService.findUserByEmail(((User)
	// SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
	// if (!this.userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
	// throw new InvalidOldPasswordException();
	// }
	// this.userService.changeUserPassword(user, passwordDto.getNewPassword());
	// return new GenericResponse(this.messages.getMessage("message.updatePasswordSuc", null,
	// locale));
	// }
	//
	// @RequestMapping(value = "/user/update/2fa", method = RequestMethod.POST)
	// @ResponseBody
	// public GenericResponse modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws
	// UnsupportedEncodingException {
	// final User user = this.userService.updateUser2FA(use2FA);
	// if (use2FA) {
	// return new GenericResponse(this.userService.generateQRUrl(user));
	// }
	// return null;
	// }
	//
	// // ============== NON-API ============
	//
	// private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final
	// Locale locale, final VerificationToken newToken,
	// final User user) {
	// final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" +
	// newToken.getToken();
	// final String message = this.messages.getMessage("message.resendToken", null, locale);
	// return this.constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl,
	// user);
	// }
	//
	// private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale
	// locale,
	// final String token, final User user) {
	// final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
	// final String message = this.messages.getMessage("message.resetPassword", null, locale);
	// return this.constructEmail("Reset Password", message + " \r\n" + url, user);
	// }
	//
	// private SimpleMailMessage constructEmail(String subject, String body, User user) {
	// final SimpleMailMessage email = new SimpleMailMessage();
	// email.setSubject(subject);
	// email.setText(body);
	// email.setTo(user.getEmail());
	// email.setFrom(this.env.getProperty("support.email"));
	// return email;
	// }

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
