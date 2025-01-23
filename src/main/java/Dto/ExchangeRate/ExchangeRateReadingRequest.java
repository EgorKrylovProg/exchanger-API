package Dto.ExchangeRate;

import Exceptions.IncorrectUrlException;

public class ExchangeRateReadingRequest {

    private String currencyPairCodes;

    public ExchangeRateReadingRequest(String currencyPairCodes) throws IncorrectUrlException {
        setCurrencyPairCodes(currencyPairCodes);
    }

    public String getCurrencyPairCodes() {
        return currencyPairCodes;
    }

    public void setCurrencyPairCodes(String currencyPairCodes) throws IncorrectUrlException {
        if (currencyPairCodes == null || currencyPairCodes.isBlank() || currencyPairCodes.length() != 6) {
            throw new IncorrectUrlException("Incorrect URL address!");
        }
        this.currencyPairCodes = currencyPairCodes;
    }
}
