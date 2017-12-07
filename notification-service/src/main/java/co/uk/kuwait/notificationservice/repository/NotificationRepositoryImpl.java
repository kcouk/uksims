package co.uk.kuwait.notificationservice.repository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import co.uk.kuwait.notificationservice.entity.Notification;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {


	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public Collection<Notification> findByNombre(@Param("name") String name) {
		return this.notificationRepository.findByName(name);
	}
}

