package com.gazbert.restsample.api.security.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Test;

/**
 * Tests User model behaves as expected.
 *
 * @author gazbert
 */
public class TestUser {

  private static final Long ADMIN_ROLE_ID = Long.valueOf("213443242342");
  private static final Long USER_ROLE_ID = Long.valueOf("21344565442342");

  private static final Long USER_1_ID = Long.valueOf("2323267789789");
  private static final String USER_1_USERNAME = "hansolo";
  private static final String USER_1_PASSWORD = "password";
  private static final String USER_1_FIRSTNAME = "Han";
  private static final String USER_1_LASTNAME = "Solo";
  private static final String USER_1_EMAIL = "han@falcon";
  private static final boolean USER_1_ENABLED = true;
  private static final Date USER_1_LAST_PASSWORD_RESET_DATE = new Date();

  @Test
  public void testInitialisationWorksAsExpected() {
    final User user = new User();
    assertNull(user.getId());
    assertNull(user.getUsername());
    assertNull(user.getPassword());
    assertNull(user.getFirstname());
    assertNull(user.getLastname());
    assertNull(user.getEmail());
    assertNull(user.getEnabled());
    assertNull(user.getLastPasswordResetDate());
    assertNull(user.getRoles());
  }

  @Test
  public void testSettersWorkAsExpected() {
    final User user = new User();

    user.setId(USER_1_ID);
    assertEquals(USER_1_ID, user.getId());

    user.setUsername(USER_1_USERNAME);
    assertEquals(USER_1_USERNAME, user.getUsername());

    user.setPassword(USER_1_PASSWORD);
    assertEquals(USER_1_PASSWORD, user.getPassword());

    user.setFirstname(USER_1_FIRSTNAME);
    assertEquals(USER_1_FIRSTNAME, user.getFirstname());

    user.setLastname(USER_1_LASTNAME);
    assertEquals(USER_1_LASTNAME, user.getLastname());

    user.setEmail(USER_1_EMAIL);
    assertEquals(USER_1_EMAIL, user.getEmail());

    user.setEnabled(USER_1_ENABLED);
    assertEquals(USER_1_ENABLED, user.getEnabled());

    user.setLastPasswordResetDate(USER_1_LAST_PASSWORD_RESET_DATE);
    assertEquals(USER_1_LAST_PASSWORD_RESET_DATE, user.getLastPasswordResetDate());

    final List<Role> roles = createRoles(user);
    user.setRoles(roles);
    assertEquals(roles, user.getRoles());
  }

  // ------------------------------------------------------------------------
  // Private utils
  // ------------------------------------------------------------------------

  private List<Role> createRoles(User user) {
    final List<User> users = Collections.singletonList(user);
    final List<Role> roles = new ArrayList<>();

    final Role role1 = new Role();
    role1.setId(ADMIN_ROLE_ID);
    role1.setName(RoleName.ROLE_ADMIN);
    role1.setUsers(users);

    final Role role2 = new Role();
    role2.setId(USER_ROLE_ID);
    role2.setName(RoleName.ROLE_USER);
    role2.setUsers(users);

    roles.add(role1);
    roles.add(role2);
    return roles;
  }
}
