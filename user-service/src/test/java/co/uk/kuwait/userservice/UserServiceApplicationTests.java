package co.uk.kuwait.userservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceApplicationTests {

	@Autowired
	private ConfigurableEnvironment environment;

	@Autowired
	private MessageRestController controller;

	@Autowired
	private ContextRefresher refresher;

	@Test
	public void contextLoads() {
		assertThat(this.controller.getMessage()).isNotEqualTo("Hello test");
		EnvironmentTestUtils.addEnvironment(this.environment, "message:Hello test");
		assertThat(this.controller.getMessage()).isNotEqualTo("Hello test");
		this.refresher.refresh();
		assertThat(this.controller.getMessage()).isEqualTo("Hello test");
	}

}


