package Dto.ExchangeRate;

import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;

public class ExchangeRateUpdatingRequest {

    private String currencyPairCodes;
    private Double rate;

    public ExchangeRateUpdatingRequest(String currencyPairCodes, String rate) throws IncorrectDataException, IncorrectUrlException, NoDataFoundException {
        setCurrencyPairCodes(currencyPairCodes);
        this.setRate(rate);
    }

    public ExchangeRateUpdatingRequest() {
    }

    public String getCurrencyPairCodes() {
        return currencyPairCodes;
    }

    public void setCurrencyPairCodes(String currencyPairCodes) throws IncorrectUrlException, NoDataFoundException {
        if (currencyPairCodes == null || currencyPairCodes.isBlank()) {
            throw new IncorrectUrlException("Incorrect URL address!");
        }
        if (currencyPairCodes.length() != 6) {
            throw new NoDataFoundException("There is no currency pair with such codes!");
        }
        this.currencyPairCodes = currencyPairCodes;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(String rate) throws IncorrectUrlException, IncorrectDataException {
        if (rate == null || rate.isBlank()) {
            throw new IncorrectUrlException("Incorrect rate parameter!");
        }
        if (Double.parseDouble(rate) <= 0) {
            throw new IncorrectDataException("Incorrect exchange rate!");
        }
        this.rate = Double.parseDouble(rate);
    }
}
