package co.uk.kuwait.notificationservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import co.uk.kuwait.notificationservice.entity.Notification;

@RepositoryRestResource(collectionResourceDescription = @Description("Notification Repository"), path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Integer>, NotificationRepositoryCustom {

	// /notification/search/by-name?rn=xxxxxxx
	@RestResource(path = "by-name")
	Collection<Notification> findByName(@Param("name") String name);


	@RestResource(path = "by-name-desc")
	Collection<Notification> findByNameAndDescription(@Param("name") String name, @Param("desc") String desc);


	@RestResource(path = "by-like-name")
	Collection<Notification> findByNameLikeOrDescription(@Param("name") String name, @Param("desc") String desc);


}


interface NotificationRepositoryCustom {

	Collection<Notification> findByNombre(@Param("name") String name);
}

