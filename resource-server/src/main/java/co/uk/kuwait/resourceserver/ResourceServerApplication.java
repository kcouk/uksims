package co.uk.kuwait.resourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableZuulProxy // Busca en eureka y mapea por nombre del servicio
@EnableDiscoveryClient
@SpringBootApplication
public class ResourceServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

}


// Test to check remote Cloud Config
@RefreshScope
@RestController
class MessageRestController {

	@Value("${server.port:Hello default client}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		return this.message;
	}
}
