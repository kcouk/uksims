package co.uk.kuwait.authservice.config;

import javax.sql.DataSource;

// import org.h2.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static String USERS_QUERY = "select username,password, enabled from user where username=?";
	private static String ROLES_QUERY =
			"select u.username, r.rolName  from user u, rol r, rolForuser rfu where u.idUser = rfu.idUser and rfu.idRol = r.idRol and u.username = ? ";

	@Value("${auth-db.datasource.url}")
	private String url;

	@Value("${auth-db.datasource.username}")
	private String username;

	@Value("${auth-db.datasource.password}")
	private String password;

	@Value("${auth-db.datasource.driver-class-name}")
	private String driverClassName;


	// @Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUrl(this.url);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		return dataSource;
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
    		auth.jdbcAuthentication().dataSource(this.dataSource() )
			.usersByUsernameQuery(USERS_QUERY)
			.authoritiesByUsernameQuery(ROLES_QUERY)
			.passwordEncoder(new ShaPasswordEncoder(256));
    }// @formatter:on


	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/oauth/token/revokeById/**").permitAll()
		.antMatchers("/tokens/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().csrf().disable();
		// @formatter:on
	}

}
