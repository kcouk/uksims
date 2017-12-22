package co.uk.kuwait.userservice.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class VerificationToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
	private User user;

	private Date expiryDate;

	public VerificationToken() {
		super();
	}

	public VerificationToken(final String token) {
		super();

		this.token = token;
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
	}

	public VerificationToken(final String token, final User user) {
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

	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.expiryDate == null ? 0 : this.expiryDate.hashCode());
		result = prime * result + (this.token == null ? 0 : this.token.hashCode());
		result = prime * result + (this.user == null ? 0 : this.user.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final VerificationToken other = (VerificationToken) obj;
		if (this.expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!this.expiryDate.equals(other.expiryDate))
			return false;
		if (this.token == null) {
			if (other.token != null)
				return false;
		} else if (!this.token.equals(other.token))
			return false;
		if (this.user == null) {
			if (other.user != null)
				return false;
		} else if (!this.user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Token [String=").append(this.token).append("]").append("[Expires").append(this.expiryDate).append("]");
		return builder.toString();
	}

}
