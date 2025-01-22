package Dto.ExchangeRate;

import Dto.Currency.CurrencyReadingResponse;

public class ExchangeRateReadingResp {

    private Integer id;
    private CurrencyReadingResponse baseCurrency;
    private CurrencyReadingResponse targetCurrency;
    private Double rate;

    public ExchangeRateReadingResp(Integer id, CurrencyReadingResponse baseCurrency, CurrencyReadingResponse targetCurrency, Double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateReadingResp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyReadingResponse getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyReadingResponse baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyReadingResponse getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyReadingResponse targetCurrency) {
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
