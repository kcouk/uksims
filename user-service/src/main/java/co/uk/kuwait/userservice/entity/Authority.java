package co.uk.kuwait.userservice.entity;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data // @ToString, @EqualsAndHashCode, @Getter on all fields, and @Setter on all non-final fields,
			// and @RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Authority implements GrantedAuthority {

	/**
	 *
	 */
	private static final long serialVersionUID = 3134504315526360686L;

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String authority;

	private String description;

	@ManyToMany(mappedBy = "authorities")
	private Collection<User> users;

	@CreatedDate
	@CreationTimestamp
	Timestamp creationDate;

	@LastModifiedDate
	@UpdateTimestamp
	Timestamp lastModified;



	public Authority(String authority) {
		this.authority = authority;
	}


}
