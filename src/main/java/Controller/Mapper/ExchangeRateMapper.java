package Controller.Mapper;

import Dto.Currency.CurrencyResponse;
import Dto.ExchangeRate.ExchangeRateCreatingRequest;
import Dto.ExchangeRate.ExchangeRateReadingRequest;
import Dto.ExchangeRate.ExchangeRateResp;
import Dto.ExchangeRate.ExchangeRateUpdatingRequest;
import Entity.Currency;
import Entity.ExchangeRate;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeRateMapper {

    CurrencyMapper currencyMapper = new CurrencyMapper();

    public ExchangeRateResp toDtoResp(ExchangeRate exchangeRate) {
        CurrencyResponse baseCurrency = currencyMapper.toDtoResponse(exchangeRate.getBaseCurrency());
        CurrencyResponse targetCurrency = currencyMapper.toDtoResponse(exchangeRate.getTargetCurrency());

        return new ExchangeRateResp(
                exchangeRate.getId(),
                baseCurrency,
                targetCurrency,
                exchangeRate.getRate()
        );
    }

    public ExchangeRateReadingRequest toReadingRequest(HttpServletRequest req) throws IncorrectUrlException {
        return new ExchangeRateReadingRequest(
                req.getPathInfo().substring(1)
        );
    }

    public ExchangeRateCreatingRequest toCreatingRequest(HttpServletRequest req) throws IncorrectUrlException, IncorrectDataException {
        return new ExchangeRateCreatingRequest(
                req.getParameter("baseCurrencyCode"),
                req.getParameter("targetCurrencyCode"),
                req.getParameter("rate")
        );
    }

    public ExchangeRateUpdatingRequest toUpdatingRequest(HttpServletRequest req) throws IncorrectDataException, IncorrectUrlException, NoDataFoundException {
        return new ExchangeRateUpdatingRequest(
                req.getPathInfo().substring(1),
                req.getParameter("rate")
        );
    }

    public ExchangeRate toEntity(ExchangeRateCreatingRequest dto) {
        Currency baseCurrency = new Currency();
        baseCurrency.setCode(dto.getBaseCurrencyCode());
        Currency targetCurrency = new Currency();
        targetCurrency.setCode(dto.getTargetCurrencyCode());

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(dto.getRate());

        return exchangeRate;
    }

    public ExchangeRate toEntity(ExchangeRateUpdatingRequest dto) {
        Currency baseCurrency = new Currency();
        String baseCode = dto.getCurrencyPairCodes().substring(0, 3);
        baseCurrency.setCode(baseCode);

        Currency targetCurrency = new Currency();
        String targetCode = dto.getCurrencyPairCodes().substring(3, 6);
        targetCurrency.setCode(targetCode);

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(dto.getRate());
        return exchangeRate;
    }



}
