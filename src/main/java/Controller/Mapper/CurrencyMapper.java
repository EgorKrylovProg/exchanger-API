package Controller.Mapper;

import Dto.Currency.CurrencyCreatingRequest;
import Dto.Currency.CurrencyReadingResponse;
import Dto.Currency.CurrencyReadingRequest;
import Entity.Currency;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;
import jakarta.servlet.http.HttpServletRequest;


public class CurrencyMapper {

    public CurrencyReadingResponse toCurrencyDto(Currency currency) {
        return new CurrencyReadingResponse(
                currency.getId(),
                currency.getFullName(),
                currency.getCode(),
                currency.getSign()
        );
    }

    public CurrencyReadingRequest toCurrencyReadingRequest(HttpServletRequest request) throws IncorrectUrlException {
        return new CurrencyReadingRequest(request.getPathInfo().substring(1));
    }

    public CurrencyCreatingRequest toCurrencyCreatingRequest(HttpServletRequest request) throws IncorrectDataException, NoDataFoundException {
        return new CurrencyCreatingRequest(
                request.getParameter("code"),
                request.getParameter("name"),
                request.getParameter("sign")
        );
    }

    public Currency toCurrency(CurrencyCreatingRequest currencyCreatingRequest) {
        Currency currency = new Currency();
        currency.setCode(currencyCreatingRequest.getCode());
        currency.setFullName(currencyCreatingRequest.getFullName());
        currency.setSign(currencyCreatingRequest.getSign());

        return currency;
    }




}
