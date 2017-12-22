package co.uk.kuwait.userservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import co.uk.kuwait.userservice.entity.Authority;
import co.uk.kuwait.userservice.utils.RestApiConstants;

// @RepositoryRestResource(collectionResourceDescription = @Description("User Repository"), path =
// "users")
public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Long> {

	Authority findByAuthority(@Param(RestApiConstants.AUTHORITY_PARAM) String authority);

	@RestResource(exported = false)
	@Override
	Iterable<Authority> findAll();
}


