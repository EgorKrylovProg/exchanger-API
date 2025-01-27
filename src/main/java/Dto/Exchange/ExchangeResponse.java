package Dto.Exchange;

import Dto.Currency.CurrencyResponse;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ExchangeResponse {

    private CurrencyResponse baseCurrency;
    private CurrencyResponse targetCurrency;
    private Double rate;
    private Double amount;
    private Double convertedAmount;

    public ExchangeResponse(CurrencyResponse baseCurrency, CurrencyResponse targetCurrency, Double rate,
                            Double amount, Double convertedAmount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = Double.parseDouble(new DecimalFormat("#.####", symbols).format(rate));
        this.amount = amount;
        this.convertedAmount = Double.parseDouble(new DecimalFormat("#.###", symbols).format(convertedAmount));
    }

    public ExchangeResponse() {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return String.format(
                "{" +
                     "\"baseCurrency\": " + baseCurrency + "," +
                     "\"targetCurrency\": " + targetCurrency + "," +
                     "\"rate\": %s," +
                     "\"amount\": %s," +
                     "\"convertedAmount\": %s" +
                "}",
                rate,  amount, convertedAmount
        );
    }
}
