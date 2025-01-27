package Controller.Mapper;

import Dto.Currency.CurrencyResponse;
import Dto.Exchange.ExchangeInformation;
import Dto.Exchange.ExchangeRequest;
import Dto.Exchange.ExchangeResponse;
import Entity.ExchangeRate;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeMapper {

    CurrencyMapper currencyMapper = new CurrencyMapper();

    public ExchangeRequest toRequest(HttpServletRequest req) throws IncorrectDataException, IncorrectUrlException {
        return new ExchangeRequest(
                req.getParameter("from"),
                req.getParameter("to"),
                req.getParameter("amount")
        );
    }

    public ExchangeResponse toResponse(ExchangeInformation exchangeInformation) {
        CurrencyResponse baseCurrencyResponse = currencyMapper.toDtoResponse(exchangeInformation.getBaseCurrency());
        CurrencyResponse targetCurrencyResponse = currencyMapper.toDtoResponse(exchangeInformation.getTargetCurrency());

        return new ExchangeResponse(
                baseCurrencyResponse,
                targetCurrencyResponse,
                exchangeInformation.getRate(),
                exchangeInformation.getAmount(),
                exchangeInformation.getConvertedAmount()
        );
    }



}
