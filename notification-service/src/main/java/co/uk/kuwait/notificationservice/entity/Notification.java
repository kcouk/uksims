package co.uk.kuwait.notificationservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notifications", catalog = "notification")
public class Notification {

	@Id
	@GeneratedValue
	private Integer id; // id
	private String name; // notification_name
	private String description;
	private String longDesc;


	public Notification(String name) {
		super();
		this.name = name;
	}


}
