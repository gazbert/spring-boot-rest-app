package com.gazbert.restsample.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Domain object representing a Market config.
 *
 * <p>In a production app, the domain objects would live in a separate module.
 *
 * @author gazbert
 */
@ApiModel
public class MarketConfig {

  @ApiModelProperty(required = true, position = 1)
  private String id;

  private String name;
  private String baseCurrency;
  private String counterCurrency;
  private boolean enabled;
  private String tradingStrategyId;

  // Required by Jackson
  public MarketConfig() {
  }

  /** Creates a MarketConfig from an existing one. */
  public MarketConfig(MarketConfig other) {
    this.id = other.id;
    this.name = other.name;
    this.baseCurrency = other.baseCurrency;
    this.counterCurrency = other.counterCurrency;
    this.enabled = other.enabled;
    this.tradingStrategyId = other.tradingStrategyId;
  }

  /** Creates a new MarketConfig. */
  public MarketConfig(
      String id,
      String name,
      String baseCurrency,
      String counterCurrency,
      boolean enabled,
      String tradingStrategyId) {

    this.id = id;
    this.name = name;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.enabled = enabled;
    this.tradingStrategyId = tradingStrategyId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }

  public void setCounterCurrency(String counterCurrency) {
    this.counterCurrency = counterCurrency;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getTradingStrategyId() {
    return tradingStrategyId;
  }

  public void setTradingStrategyId(String tradingStrategyId) {
    this.tradingStrategyId = tradingStrategyId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarketConfig that = (MarketConfig) o;
    return Objects.equal(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .add("baseCurrency", baseCurrency)
        .add("counterCurrency", counterCurrency)
        .add("enabled", enabled)
        .add("tradingStrategyId", tradingStrategyId)
        .toString();
  }
}
