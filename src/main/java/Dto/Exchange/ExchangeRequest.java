package Dto.Exchange;

import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;

public class ExchangeRequest {

    private String fromCurrency;
    private String toCurrency;
    private Double amount;

    public ExchangeRequest(String fromCurrency, String toCurrency, String amount) throws IncorrectDataException, IncorrectUrlException {
        setFromCurrency(fromCurrency);
        setToCurrency(toCurrency);
        setAmount(amount);
    }

    public ExchangeRequest() {
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) throws IncorrectUrlException, IncorrectDataException {
        if (fromCurrency == null || fromCurrency.isBlank()) {
            throw new IncorrectUrlException("There is no data about the base currency code!");
        }
        if (fromCurrency.length() != 3) {
            throw new IncorrectDataException("Incorrect base currency code!");
        }
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) throws IncorrectUrlException, IncorrectDataException {
        if (toCurrency == null || toCurrency.isBlank()) {
            throw new IncorrectUrlException("There is no data about the target currency code!");
        }
        if (toCurrency.length() != 3) {
            throw new IncorrectDataException("Incorrect target currency code!");
        }
        this.toCurrency = toCurrency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws IncorrectUrlException, IncorrectDataException {
        if (amount == null || amount.isBlank()) {
            throw new IncorrectUrlException("There is no data about the amount!");
        }
        if (Double.parseDouble(amount) <= 0) {
            throw new IncorrectDataException("Incorrect amount!");
        }
        this.amount = Double.parseDouble(amount);
    }
}
