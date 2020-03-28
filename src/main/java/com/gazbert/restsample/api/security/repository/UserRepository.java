package com.gazbert.restsample.api.security.repository;

import com.gazbert.restsample.api.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * JPA repository for looking up User details.
 *
 * @author gazbert
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
