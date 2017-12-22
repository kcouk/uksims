package co.uk.kuwait.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import co.uk.kuwait.userservice.entity.User;
import co.uk.kuwait.userservice.utils.RestApiConstants;

// @RepositoryRestResource(collectionResourceDescription = @Description("User Repository"), path =
// "users")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);


	// /user/search/by-name?rn=xxxxxxx
	@RestResource(path = "by-username")
	User findByUsername(@Param(RestApiConstants.USERNAME_PARAM) String username);

	@RestResource(path = "by-username-enabled")
	@Query("select u from User u where u.username = :username and u.enabled = true " + "and u.accountNonLocked = true "
			+ "and u.accountNonExpired = true " + "and u.credentialsNonExpired = true")
	User findUserForLogin(@Param(RestApiConstants.USERNAME_PARAM) String username);

	// @RestResource(exported = false)
	// User findByRegistrationConfirmationToken(String registrationConfirmationToken);


	// @Override
	// void delete(User user);



}


