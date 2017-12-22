package co.uk.kuwait.userservice.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
public class SecurityConfig {

	private static final String USERS_QUERY = "select username,password, enabled from Users where username=?";
	private static final String ROLES_QUERY =
			"select u.username, r.authority  from Users u, Authority r, Users_Authority rfu where u.id = rfu.users_id and rfu.authorities_id = r.id and u.username = ? ";

	@Autowired
	private DataSource dataSource;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ShaPasswordEncoder(256);
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}



	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
    		auth.jdbcAuthentication().dataSource(this.dataSource)
			.usersByUsernameQuery(USERS_QUERY)
			.authoritiesByUsernameQuery(ROLES_QUERY)
			.passwordEncoder(new ShaPasswordEncoder(256));
    }// @formatter:on


}
