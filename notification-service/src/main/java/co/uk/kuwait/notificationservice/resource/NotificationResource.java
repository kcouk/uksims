package co.uk.kuwait.notificationservice.resource;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.uk.kuwait.notificationservice.entity.Notification;
import co.uk.kuwait.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/notifications")
public class NotificationResource {

	@Autowired
	NotificationRepository notificationRepository;


	@GetMapping("/all")
	public List<Notification> getAll() {

		return this.notificationRepository.findAll();
	}


	@GetMapping("/{name}")
	public Collection<Notification> getNotification(@PathVariable("name") final String name) {
		return this.notificationRepository.findByName(name);

	}

	@GetMapping("/nombre/{name}")
	public Collection<Notification> getPolla(@PathVariable("name") final String name) {
		return this.notificationRepository.findByNombre(name);

	}


	@GetMapping("/id/{id}")
	public Notification getId(@PathVariable("id") final Integer id) {
		log.warn("getNotification(" + id + ")");
		return this.notificationRepository.findOne(id);
	}

	// @GetMapping("/update/{id}/{name}")
	// public Notification update(@PathVariable("id") final Integer id, @PathVariable("name")
	// final String name) {
	//
	//
	// Notification notifications = getId(id);
	// notifications.setName(name);
	//
	// return notificationRepository.save(notifications);
	// }



	@GetMapping("/allHATEOAS")
	public @ResponseBody ResponseEntity<?> getAllHATEOAS() {

		List<Notification> notifications = this.notificationRepository.findAll();

		Resources<Notification> resources = new Resources<>(notifications);

		return ResponseEntity.ok(resources);


	}



}
