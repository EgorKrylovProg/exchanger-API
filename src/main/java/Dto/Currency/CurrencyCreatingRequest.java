package Dto.Currency;

import Exceptions.IncorrectDataException;
import Exceptions.NoDataFoundException;

public class CurrencyCreatingRequest {

    private String code;
    private String fullName;
    private String sign;

    public CurrencyCreatingRequest(String code, String fullName, String sign) throws IncorrectDataException, NoDataFoundException {
        setCode(code);
        setFullName(fullName);
        setSign(sign);
    }

    public CurrencyCreatingRequest() {
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setCode(String code) throws NoDataFoundException, IncorrectDataException {
        if (code == null || code.isBlank()) {
            throw new NoDataFoundException("There is no data about the currency code!");
        }
        if (code.length() != 3) {
            throw new IncorrectDataException("Incorrect currency code!");
        }
        this.code = code;
    }

    public void setFullName(String fullName) throws NoDataFoundException {
        if (fullName == null || fullName.isBlank()) {
            throw new NoDataFoundException("There is no information about the name of the currency!");
        }
        this.fullName = fullName;
    }

    public void setSign(String sign) throws IncorrectDataException, NoDataFoundException {
        if(sign == null || sign.isBlank()) {
            throw new NoDataFoundException("There is no information about the currency symbol!");
        }
        if(sign.length() > 2) {
            throw new IncorrectDataException("Incorrect currency symbol!");
        }
        this.sign = sign;
    }
}
