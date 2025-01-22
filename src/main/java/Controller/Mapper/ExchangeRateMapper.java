package Controller.Mapper;

import Dto.Currency.CurrencyReadingResponse;
import Dto.ExchangeRate.ExchangeRateReadingResp;
import Entity.ExchangeRate;

public class ExchangeRateMapper {

    CurrencyMapper currencyMapper = new CurrencyMapper();

    public ExchangeRateReadingResp toExchangeRateReadingResp(ExchangeRate exchangeRate) {
        CurrencyReadingResponse baseCurrency = currencyMapper.toCurrencyReadingResponse(exchangeRate.getBaseCurrency());
        CurrencyReadingResponse targetCurrency = currencyMapper.toCurrencyReadingResponse(exchangeRate.getTargetCurrency());

        return new ExchangeRateReadingResp(
                exchangeRate.getId(),
                baseCurrency,
                targetCurrency,
                exchangeRate.getRate()
        );
    }

}
