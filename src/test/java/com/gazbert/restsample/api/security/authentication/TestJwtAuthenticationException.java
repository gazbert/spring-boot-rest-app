package com.gazbert.restsample.api.security.authentication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests JWT Authentication Exception is created as expected.
 *
 * @author gazbert
 */
public class TestJwtAuthenticationException {

  private static final String ERROR_MSG = "Failed to extract expiration claim from token!";
  private static final RuntimeException CAUSE = new RuntimeException("The cause of the exception");

  @Test
  public void testCreationOfExceptionIsAsExpected() {
    final JwtAuthenticationException exception = new JwtAuthenticationException(ERROR_MSG);
    assertEquals(ERROR_MSG, exception.getMessage());
  }

  @Test
  public void testCreationOfExceptionWithCauseIsAsExpected() {
    final JwtAuthenticationException exception = new JwtAuthenticationException(ERROR_MSG, CAUSE);
    assertEquals(ERROR_MSG, exception.getMessage());
    assertEquals(CAUSE, exception.getCause());
  }
}
