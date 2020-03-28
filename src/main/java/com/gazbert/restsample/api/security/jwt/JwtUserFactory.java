package com.gazbert.restsample.api.security.jwt;

import com.gazbert.restsample.api.security.model.Role;
import com.gazbert.restsample.api.security.model.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Creates a JWT User that has been authenticated successfully.
 *
 * @author gazbert
 */
public final class JwtUserFactory {

  private JwtUserFactory() {
  }

  /**
   * Creates a JWT User.
   *
   * @param user the user details from the database.
   * @return a JWT User.
   */
  public static JwtUser create(User user) {
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getFirstname(),
        user.getLastname(),
        user.getPassword(),
        user.getEmail(),
        user.getEnabled(),
        user.getLastPasswordResetDate().getTime(),
        mapUserRolesToGrantedAuthorities(user.getRoles()),
        user.getRoles());
  }

  private static List<GrantedAuthority> mapUserRolesToGrantedAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
  }
}
