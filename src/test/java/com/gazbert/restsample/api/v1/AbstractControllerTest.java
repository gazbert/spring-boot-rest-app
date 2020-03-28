package com.gazbert.restsample.api.v1;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for all Controller test classes.
 *
 * @author gazbert
 */
public abstract class AbstractControllerTest {

  private static final String API_ENDPOINT_BASE_URI = "/api/v1";
  protected static final String CONFIG_ENDPOINT_BASE_URI = API_ENDPOINT_BASE_URI + "/config";

  // This must match a user's USERNAME in the user table in src/test/resources/import.sql
  protected static final String VALID_USER_NAME = "user";

  // This must match a user's PASSWORD in the user table in src/test/resources/import.sql
  protected static final String VALID_USER_PASSWORD = "user";

  // This must match a admin's USERNAME in the user table in src/test/resources/import.sql
  protected static final String VALID_ADMIN_NAME = "admin";

  // This must match a admin's PASSWORD in the user table in src/test/resources/import.sql
  protected static final String VALID_ADMIN_PASSWORD = "admin";

  // Used to convert Java objects into JSON
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired protected WebApplicationContext ctx;

  @Autowired protected FilterChainProxy springSecurityFilterChain;

  protected MockMvc mockMvc;

  @Autowired
  protected void setConverters(HttpMessageConverter<?>[] converters) {
    mappingJackson2HttpMessageConverter =
        Arrays.stream(converters)
            .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

    Assert.assertNotNull(
        "The JSON message converter must not be null", mappingJackson2HttpMessageConverter);
  }

  // --------------------------------------------------------------------------
  // Shared utils
  // --------------------------------------------------------------------------

  protected String buildAuthorizationHeaderValue(String username, String password) {
    return "Basic "
        + new String(
            Base64Utils.encode((username + ":" + password).getBytes(StandardCharsets.UTF_8)),
            Charset.forName("UTF-8"));
  }

  /*
   * Builds a JWT response.
   * Kudos to @royclarkson for his OAuth2 version:
   * https://github.com/royclarkson/spring-rest-service-oauth
   */
  protected String getJwt(String username, String password) throws Exception {

    final String content =
        mockMvc
            .perform(
                post("/api/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonify(new UsernameAndPassword(username, password))))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.token", is(notNullValue())))
            .andReturn()
            .getResponse()
            .getContentAsString();

    final ObjectMapper objectMapper = new ObjectMapper();
    final JwtResponse jwtResponse = objectMapper.readValue(content, JwtResponse.class);
    return jwtResponse.getToken();
  }

  protected String jsonify(Object objectToJsonify) throws IOException {
    final MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    mappingJackson2HttpMessageConverter.write(
        objectToJsonify, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

  // --------------------------------------------------------------------------
  // Private helpers
  // --------------------------------------------------------------------------

  private static class UsernameAndPassword {

    private String username;
    private String password;

    UsernameAndPassword(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  private static class JwtResponse {

    private String token;

    // empty constructor needed by Jackson
    public JwtResponse() {
    }

    String getToken() {
      return token;
    }

    void setToken(String token) {
      this.token = token;
    }
  }
}
