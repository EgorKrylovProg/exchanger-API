package Controller.Mapper;

import Dto.Currency.CurrencyCreatingRequest;
import Dto.Currency.CurrencyResponse;
import Dto.Currency.CurrencyReadingRequest;
import Entity.Currency;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import jakarta.servlet.http.HttpServletRequest;


public class CurrencyMapper {

    public CurrencyResponse toDtoResponse(Currency currency) {
        return new CurrencyResponse(
                currency.getId(),
                currency.getFullName(),
                currency.getCode(),
                currency.getSign()
        );
    }

    public CurrencyReadingRequest toReadingRequest(HttpServletRequest request) throws IncorrectUrlException {
        return new CurrencyReadingRequest(request.getPathInfo().substring(1));
    }

    public CurrencyCreatingRequest toCreatingRequest(HttpServletRequest request) throws IncorrectDataException, IncorrectUrlException {
        return new CurrencyCreatingRequest(
                request.getParameter("code"),
                request.getParameter("name"),
                request.getParameter("sign")
        );
    }

    public Currency toEntity(CurrencyCreatingRequest currencyCreatingRequest) {
        Currency currency = new Currency();
        currency.setCode(currencyCreatingRequest.getCode());
        currency.setFullName(currencyCreatingRequest.getFullName());
        currency.setSign(currencyCreatingRequest.getSign());

        return currency;
    }




}
