package com.gazbert.restsample.services;

import com.gazbert.restsample.domain.MarketConfig;
import java.util.List;

/**
 * The Market configuration service.
 *
 * <p>In a production app, the services would live in a separate module.
 *
 * @author gazbert
 */
public interface MarketConfigService {

  List<MarketConfig> getAllMarketConfig();

  MarketConfig getMarketConfig(String id);

  MarketConfig createMarketConfig(MarketConfig config);

  MarketConfig updateMarketConfig(MarketConfig config);

  MarketConfig deleteMarketConfig(String id);
}
