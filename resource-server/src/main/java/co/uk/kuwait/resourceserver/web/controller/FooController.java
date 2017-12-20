package co.uk.kuwait.resourceserver.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.uk.kuwait.resourceserver.web.dto.Foo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FooController {

	public FooController() {
		super();
	}

	// API - read
	@PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('read')")
	@RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
	@ResponseBody
	public Foo findById(@PathVariable final long id, Principal principal, Authentication authentication) {
		log.info(principal.getName());
		log.info(authentication.getName());
		log.info(authentication.getAuthorities().toString());

		final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		log.info("User has authorities: " + userDetails.getAuthorities());

		return new Foo(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
	}

	// API - write
	@PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('write')")
	@RequestMapping(method = RequestMethod.POST, value = "/foos")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Foo create(@RequestBody final Foo foo) {
		foo.setId(Long.parseLong(randomNumeric(2)));
		return foo;
	}

}
