package co.uk.kuwait.userservice.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.registration.OnRegistrationCompleteEvent;
import co.uk.kuwait.userservice.service.IUserService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	@Autowired
	private IUserService service;

	@Autowired
	private MessageSource messages;

	// @Autowired
	// private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	// API

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final User user = event.getUser();
		final String token = UUID.randomUUID().toString();
		this.service.createVerificationTokenForUser(user, token);
		log.info("Verification token: " + token);

		log.info("confirmationUrl = " + event.getAppUrl() + "/registrationConfirm.html?token=" + token);


		// final SimpleMailMessage email = this.constructEmailMessage(event, user, token);
		// this.mailSender.send(email);
	}

	//

	// private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
	// final User user, final String token) {
	// final String recipientAddress = user.getEmail();
	// final String subject = "Registration Confirmation";
	// final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
	// final String message = this.messages.getMessage("message.regSucc", null, event.getLocale());
	// final SimpleMailMessage email = new SimpleMailMessage();
	// email.setTo(recipientAddress);
	// email.setSubject(subject);
	// email.setText(message + " \r\n" + confirmationUrl);
	// email.setFrom(this.env.getProperty("support.email"));
	// return email;
	// }

}
