package com.gazbert.restsample.api.security.service;

import com.gazbert.restsample.api.security.jwt.JwtUserFactory;
import com.gazbert.restsample.api.security.model.User;
import com.gazbert.restsample.api.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User Details service for loading user details from the repository.
 *
 * @author gazbert
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    final User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      return JwtUserFactory.create(user);
    }
  }
}
