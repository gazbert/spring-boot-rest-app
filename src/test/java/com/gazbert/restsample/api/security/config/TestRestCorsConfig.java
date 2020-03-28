package com.gazbert.restsample.api.security.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.web.filter.CorsFilter;

/**
 * Tests the REST CORS config filter is created as expected.
 *
 * @author gazbert
 */
public class TestRestCorsConfig {

  @Test
  public void testInitialisationWorksAsExpected() {
    final RestCorsConfig restCorsConfig = new RestCorsConfig();
    final CorsFilter corsFilter = restCorsConfig.corsFilter();
    assertNotNull(corsFilter);
  }
}
