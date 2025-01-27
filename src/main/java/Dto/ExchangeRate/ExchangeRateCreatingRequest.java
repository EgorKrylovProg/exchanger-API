package Dto.ExchangeRate;

import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;

public class ExchangeRateCreatingRequest {

    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private Double rate;

    public ExchangeRateCreatingRequest(String baseCurrencyCode, String targetCurrencyCode, String rate) throws IncorrectUrlException, IncorrectDataException {
        setBaseCurrencyCode(baseCurrencyCode);
        setTargetCurrencyCode(targetCurrencyCode);
        setRate(rate);
    }

    public ExchangeRateCreatingRequest() {
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public Double getRate() {
        return rate;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) throws IncorrectDataException, IncorrectUrlException {
        if (baseCurrencyCode == null || baseCurrencyCode.isBlank()) {
            throw new IncorrectUrlException("There is no data about the base currency code!");
        }
        if (baseCurrencyCode.length() != 3) {
            throw new IncorrectDataException("Incorrect base currency code!");
        }
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) throws IncorrectUrlException, IncorrectDataException {
        if (targetCurrencyCode == null || targetCurrencyCode.isBlank()) {
            throw new IncorrectUrlException("There is no data about the target currency code!");
        }
        if (targetCurrencyCode.length() != 3) {
            throw new IncorrectDataException("Incorrect target currency code!");
        }
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public void setRate(String rate) throws IncorrectUrlException, IncorrectDataException {
        if (rate == null || rate.isBlank()) {
            throw new IncorrectUrlException("There is no data about the rate!");
        }
        if (Double.parseDouble(rate) <= 0) {
            throw new IncorrectDataException("Incorrect exchange rate!");
        }
        this.rate = Double.parseDouble(rate);
    }
}
