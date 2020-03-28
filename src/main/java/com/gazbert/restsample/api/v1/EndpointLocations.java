package com.gazbert.restsample.api.v1;

/**
 * Base paths for v1 REST endpoints.
 *
 * @author gazbert
 */
public final class EndpointLocations {

  /** Base path for entire REST API. */
  private static final String API_ENDPOINT_BASE_URI = "/api/v1";

  /** Base path for configuration REST endpoints. */
  public static final String CONFIG_ENDPOINT_BASE_URI = API_ENDPOINT_BASE_URI + "/config";

  private EndpointLocations() {
  }
}
