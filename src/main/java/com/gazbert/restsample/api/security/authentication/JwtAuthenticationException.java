package com.gazbert.restsample.api.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * This runtime exception is thrown if JWT authentication fails.
 *
 * @author gazbert
 */
public class JwtAuthenticationException extends AuthenticationException {

  private static final long serialVersionUID = -5066890753686004758L;

  public JwtAuthenticationException(String msg, Throwable e) {
    super(msg, e);
  }

  public JwtAuthenticationException(String msg) {
    super(msg);
  }
}
