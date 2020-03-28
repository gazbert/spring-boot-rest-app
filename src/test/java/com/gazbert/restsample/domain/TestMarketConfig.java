package com.gazbert.restsample.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests a MarketConfig domain object behaves as expected.
 *
 * @author gazbert
 */
public class TestMarketConfig {

  private static final String ID = "gemini_usd/btc";
  private static final String NAME = "BTC/USD";
  private static final String BASE_CURRENCY = "BTC";
  private static final String COUNTER_CURRENCY = "USD";
  private static final boolean IS_ENABLED = true;
  private static final String TRADING_STRATEGY = "macd_trend_follower";

  @Test
  public void testInitialisationWorksAsExpected() {
    final MarketConfig marketConfig =
        new MarketConfig(ID, NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);

    assertEquals(NAME, marketConfig.getName());
    assertEquals(ID, marketConfig.getId());
    assertEquals(BASE_CURRENCY, marketConfig.getBaseCurrency());
    assertEquals(COUNTER_CURRENCY, marketConfig.getCounterCurrency());
    assertEquals(IS_ENABLED, marketConfig.isEnabled());
    assertEquals(TRADING_STRATEGY, marketConfig.getTradingStrategyId());
  }

  @Test
  public void testSettersWorkAsExpected() {
    final MarketConfig marketConfig = new MarketConfig();
    assertNull(marketConfig.getId());
    assertNull(marketConfig.getName());
    assertNull(marketConfig.getBaseCurrency());
    assertNull(marketConfig.getCounterCurrency());
    assertFalse(marketConfig.isEnabled());
    assertNull(marketConfig.getTradingStrategyId());

    marketConfig.setId(ID);
    assertEquals(ID, marketConfig.getId());

    marketConfig.setName(NAME);
    assertEquals(NAME, marketConfig.getName());

    marketConfig.setBaseCurrency(BASE_CURRENCY);
    assertEquals(BASE_CURRENCY, marketConfig.getBaseCurrency());

    marketConfig.setCounterCurrency(COUNTER_CURRENCY);
    assertEquals(COUNTER_CURRENCY, marketConfig.getCounterCurrency());

    marketConfig.setEnabled(IS_ENABLED);
    assertEquals(IS_ENABLED, marketConfig.isEnabled());

    marketConfig.setTradingStrategyId(TRADING_STRATEGY);
    assertEquals(TRADING_STRATEGY, marketConfig.getTradingStrategyId());
  }

  @Test
  public void testCloningWorksAsExpected() {
    final MarketConfig marketConfig =
        new MarketConfig(ID, NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);
    final MarketConfig clonedMarketConfig = new MarketConfig(marketConfig);

    assertEquals(clonedMarketConfig, marketConfig);
  }

  @Test
  public void testEqualsWorksAsExpected() {
    final MarketConfig market1 =
        new MarketConfig(ID, NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);
    final MarketConfig market2 =
        new MarketConfig(
            "different-id", NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);
    final MarketConfig market3 =
        new MarketConfig(
            ID, "different-name", BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);

    assertEquals(market1, market1);
    assertNotEquals(market1, market2);
    assertEquals(market1, market3);
  }

  @Test
  public void testHashCodeWorksAsExpected() {
    final MarketConfig market1 =
        new MarketConfig(ID, NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);
    final MarketConfig market2 =
        new MarketConfig(
            "different-id", NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);
    final MarketConfig market3 =
        new MarketConfig(
            ID, "different-name", BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);

    assertEquals(market1.hashCode(), market1.hashCode());
    assertNotEquals(market1.hashCode(), market2.hashCode());
    assertEquals(market1.hashCode(), market3.hashCode());
  }

  @Test
  public void testToStringWorksAsExpected() {
    final MarketConfig market1 =
        new MarketConfig(ID, NAME, BASE_CURRENCY, COUNTER_CURRENCY, IS_ENABLED, TRADING_STRATEGY);

    assertEquals(
        "MarketConfig{id=gemini_usd/btc, name=BTC/USD, baseCurrency=BTC,"
            + " counterCurrency=USD, enabled=true, tradingStrategyId=macd_trend_follower}",
        market1.toString());
  }
}
