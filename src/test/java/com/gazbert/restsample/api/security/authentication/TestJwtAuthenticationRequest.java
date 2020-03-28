
package com.gazbert.restsample.api.security.authentication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a JWT Authentication Request behaves as expected.
 *
 * @author gazbert
 */
public class TestJwtAuthenticationRequest {

  private static final String USERNAME = "user1";
  private static final String PASSWORD = "InSearchOfLostTime";
  private static final String USERNAME2 = "user2";
  private static final String PASSWORD2 = "InSearchOfLostTime2";

  @Test
  public void testEmptyConstructorWorksAsExpected() {
    final JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest();
    assertEquals("", jwtAuthenticationRequest.getUsername());
    assertEquals("", jwtAuthenticationRequest.getPassword());
  }

  @Test
  public void testArgsConstructorWorksAsExpected() {
    final JwtAuthenticationRequest jwtAuthenticationRequest =
        new JwtAuthenticationRequest(USERNAME, PASSWORD);
    assertEquals(USERNAME, jwtAuthenticationRequest.getUsername());
    assertEquals(PASSWORD, jwtAuthenticationRequest.getPassword());
  }

  @Test
  public void testSettersWorkAsExpected() {
    final JwtAuthenticationRequest jwtAuthenticationRequest =
        new JwtAuthenticationRequest(USERNAME, PASSWORD);
    jwtAuthenticationRequest.setUsername(USERNAME2);
    assertEquals(USERNAME2, jwtAuthenticationRequest.getUsername());
    jwtAuthenticationRequest.setPassword(PASSWORD2);
    assertEquals(PASSWORD2, jwtAuthenticationRequest.getPassword());
  }
}
