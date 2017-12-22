package co.uk.kuwait.userservice.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "id")
	private User user;

	private Date expiryDate;


	public PasswordResetToken(final String token) {
		super();

		this.token = token;
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
	}

	public PasswordResetToken(final String token, final User user) {
		super();

		this.token = token;
		this.user = user;
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
	}


	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
	}


}
