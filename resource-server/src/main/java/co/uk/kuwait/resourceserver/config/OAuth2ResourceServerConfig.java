package co.uk.kuwait.resourceserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;

	//

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		    .and()
		  //  .authorizeRequests().anyRequest().permitAll()
        .requestMatchers()
        .and()
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .antMatchers("/actuator/**", "/api-docs/**").permitAll()
        .antMatchers("/notification-service/**", "/user-service/**").hasAnyAuthority("ADMIN","ADMINISTRATOR","ROLE_ADMIN","ROLE_ADMINISTRATOR")
        .antMatchers("/gateway/**" ).authenticated();

	// @formatter:on
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(this.tokenServices());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(this.tokenStore());
		return defaultTokenServices;
	}

	// JDBC token store configuration

	@Bean
	@ConfigurationProperties(prefix = "oauth2-db.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(this.dataSource());
	}

}
