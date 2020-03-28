package com.gazbert.restsample.api.security.service;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import com.gazbert.restsample.api.security.jwt.JwtUser;
import com.gazbert.restsample.api.security.jwt.JwtUserFactory;
import com.gazbert.restsample.api.security.model.User;
import com.gazbert.restsample.api.security.repository.UserRepository;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Tests the JWT User Details service behaves as expected.
 *
 * @author gazbert
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JwtUserFactory.class})
public class TestJwtUserDetailsService {

  private static final String KNOWN_USERNAME = "known-username";
  private static final String UNKNOWN_USERNAME = "unknown-username";

  private UserRepository userRepository;

  @Before
  public void setup() {
    userRepository = PowerMock.createMock(UserRepository.class);
  }

  @Test
  public void whenLoadByUsernameCalledWithKnownUsernameThenExpectUserDetailsToBeReturned() {
    PowerMock.mockStatic(JwtUserFactory.class);
    final User user = PowerMock.createMock(User.class);
    final JwtUser jwtUser = EasyMock.createMock(JwtUser.class);

    expect(userRepository.findByUsername(KNOWN_USERNAME)).andStubReturn(user);
    expect(JwtUserFactory.create(eq(user))).andStubReturn(jwtUser);
    PowerMock.replayAll();

    final JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);
    final JwtUser userDetails = (JwtUser) jwtUserDetailsService.loadUserByUsername(KNOWN_USERNAME);
    assertEquals(jwtUser, userDetails);

    PowerMock.verifyAll();
  }

  @Test(expected = UsernameNotFoundException.class)
  public void whenLoadByUsernameCalledWithUnknownUsernameThenExpectUsernameNotFoundException() {
    expect(userRepository.findByUsername(UNKNOWN_USERNAME)).andStubReturn(null);
    PowerMock.replayAll();

    final JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);
    jwtUserDetailsService.loadUserByUsername(UNKNOWN_USERNAME);

    PowerMock.verifyAll();
  }
}
