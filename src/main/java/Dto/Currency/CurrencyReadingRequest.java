package Dto.Currency;

import Exceptions.IncorrectUrlException;

public class CurrencyReadingRequest {

    private String code;

    public CurrencyReadingRequest(String code) throws IncorrectUrlException {
        setCode(code);
    }

    public CurrencyReadingRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws IncorrectUrlException {
        if (code.isBlank() || code.length() != 3) {
            throw new IncorrectUrlException("Incorrect currency code in the address!");
        }
        this.code = code;
    }
}
