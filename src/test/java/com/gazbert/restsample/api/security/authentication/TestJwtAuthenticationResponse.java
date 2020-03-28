package com.gazbert.restsample.api.security.authentication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a JWT Authentication Response behaves as expected.
 *
 * @author gazbert
 */
public class TestJwtAuthenticationResponse {

  private static final String JWT = "the.jwt.string";
  private static final String ANOTHER_JWT = "another.jwt.string";

  @Test
  public void testEmptyConstructorWorksAsExpected() {
    final JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
    assertEquals("", jwtAuthenticationResponse.getToken());
  }

  @Test
  public void testSetterAndGettersWorkAsExpected() {
    final JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(JWT);
    jwtAuthenticationResponse.setToken(ANOTHER_JWT);
    assertEquals(ANOTHER_JWT, jwtAuthenticationResponse.getToken());
  }
}
