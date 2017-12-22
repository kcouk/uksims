package co.uk.kuwait.userservice.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import co.uk.kuwait.userservice.entity.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	*
	*/
	private static final long serialVersionUID = -558660716728784981L;
	private final String appUrl;
	private final Locale locale;
	private final User user;

	public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	//

	public String getAppUrl() {
		return this.appUrl;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public User getUser() {
		return this.user;
	}

}
