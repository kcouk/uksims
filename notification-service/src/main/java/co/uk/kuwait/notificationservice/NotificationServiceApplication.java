package co.uk.kuwait.notificationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@EnableJpaRepositories(basePackages = "co.uk.kuwait.notificationservice.repository")
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
}


// Test to check remote Cloud Config
@RefreshScope
@RestController
@Slf4j
class MessageRestController {

	@Value("${message:Hello default}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		log.debug("Este es un mensaje de debug: " + this.message);
		log.error("Este es un mensaje de error: " + this.message);
		log.info("Este es un mensaje de info: " + this.message);
		log.warn("Este es un mensaje de warning: " + this.message);
		log.trace("Este es un mensaje de trace: " + this.message);
		log.debug("Este es un mensaje de debug: " + this.message);
		return this.message;
	}

}
