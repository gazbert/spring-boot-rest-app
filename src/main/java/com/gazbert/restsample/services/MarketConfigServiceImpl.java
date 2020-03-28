package com.gazbert.restsample.services;

import com.gazbert.restsample.domain.MarketConfig;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the Market config service.
 *
 * <p>In a production app, the services would live in a separate module.
 *
 * <p>Stubbed out backend - these would normally call a repository.
 *
 * @author gazbert
 */
@Service("marketConfigService")
@Transactional
public class MarketConfigServiceImpl implements MarketConfigService {

  private static final Logger LOG = LogManager.getLogger();
  private List<MarketConfig> allMarkets;

  @Autowired
  public MarketConfigServiceImpl() {
    allMarkets = initMarketConfig();
  }

  @Override
  public List<MarketConfig> getAllMarketConfig() {
    return allMarkets;
  }

  @Override
  public MarketConfig getMarketConfig(String id) {
    LOG.info(() -> "Fetching Market config for id: " + id);
    for (final MarketConfig marketConfig : allMarkets) {
      if (marketConfig.getId().equals(id)) {
        return marketConfig;
      }
    }
    return null;
  }

  @Override
  public MarketConfig updateMarketConfig(MarketConfig config) {
    LOG.info(() -> "About to update Market config: " + config);
    if (allMarkets.contains(config)) {
      allMarkets.add(config);
      return config;
    }
    return null;
  }

  @Override
  public MarketConfig createMarketConfig(MarketConfig config) {
    LOG.info(() -> "About to create Market config: " + config);
    if (!allMarkets.contains(config)) {
      allMarkets.add(config);
      return config;
    }
    return null;
  }

  @Override
  public MarketConfig deleteMarketConfig(String id) {
    LOG.info(() -> "About to delete Market config for id: " + id);
    for (final MarketConfig marketConfig : allMarkets) {
      if (marketConfig.getId().equals(id)) {
        allMarkets.remove(marketConfig);
        return marketConfig;
      }
    }
    return null;
  }

  // ------------------------------------------------------------------------
  // Canned data
  // ------------------------------------------------------------------------

  private List<MarketConfig> initMarketConfig() {
    final MarketConfig market1Config =
        new MarketConfig("btc_usd", "BTC/USD", "BTC", "USD", true, "scalper-strategy");
    final MarketConfig market2Config =
        new MarketConfig("btc_gbp", "BTC/GBP", "BTC", "GBP", false, "macd-strategy");

    allMarkets = new ArrayList<>();
    allMarkets.add(market1Config);
    allMarkets.add(market2Config);
    return allMarkets;
  }
}
