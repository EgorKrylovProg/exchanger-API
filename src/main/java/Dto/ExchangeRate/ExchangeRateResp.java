package Dto.ExchangeRate;

import Dto.Currency.CurrencyResponse;

public class ExchangeRateResp {

    private Integer id;
    private CurrencyResponse baseCurrency;
    private CurrencyResponse targetCurrency;
    private Double rate;

    public ExchangeRateResp(Integer id, CurrencyResponse baseCurrency, CurrencyResponse targetCurrency, Double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateResp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyResponse getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyResponse baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyResponse getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyResponse targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format(
                    "{" +
                        "\"id\": %d," +
                        "\"baseCurrency\": " + baseCurrency + "," +
                        "\"targetCurrency\": " + targetCurrency + "," +
                        "\"rate\": %s" +
                    "}",
                    id, rate
        );
    }
}
