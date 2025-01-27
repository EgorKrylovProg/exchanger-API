package Service.Impl;

import DAO.DataBaseSqlite;
import DAO.Impl.CurrenciesDAO;
import DAO.Impl.ExchangeRateDAO;
import DAO.Interfaces.DAO;
import DAO.Interfaces.UpdateDAO;
import Dto.Exchange.ExchangeInformation;
import Dto.Exchange.ExchangeRequest;
import Entity.Currency;
import Entity.ExchangeRate;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.util.Optional;

public class ExchangerService {

    UpdateDAO<String, ExchangeRate> exchangeRateDAO = new ExchangeRateDAO(new DataBaseSqlite());
    DAO<String, Currency> currencyDAO = new CurrenciesDAO(new DataBaseSqlite());

    public ExchangeInformation exchangeCurrency(ExchangeRequest exchangeRequest) throws NoDataFoundException, DatabaseAccessException {
        Currency baseCurrency = currencyDAO.get(exchangeRequest.getFromCurrency())
                .orElseThrow(() -> new NoDataFoundException("There is no information about the currency you want to exchange!"));
        Currency targetCurrency = currencyDAO.get(exchangeRequest.getToCurrency())
                .orElseThrow(() -> new NoDataFoundException("There is no information about the currency you want to receive!"));

        Double rate = getRate(exchangeRequest.getFromCurrency(), exchangeRequest.getToCurrency());
        Double convertedAmount = convertCurrency(exchangeRequest.getAmount(), rate);

        return new ExchangeInformation(
                baseCurrency,
                targetCurrency,
                rate,
                exchangeRequest.getAmount(),
                convertedAmount
        );
    }

    private Double getRate(String baseCurrencyCode, String targetCurrencyCode) throws DatabaseAccessException, NoDataFoundException {

        Optional<ExchangeRate> exchangeRate = exchangeRateDAO.get(baseCurrencyCode + targetCurrencyCode);
        if (exchangeRate.isPresent()) {
            return exchangeRate.get().getRate();
        }

        exchangeRate = exchangeRateDAO.get(targetCurrencyCode + baseCurrencyCode);
        if (exchangeRate.isPresent()) {
            return 1.0 / exchangeRate.get().getRate();
        }

        Optional<ExchangeRate> firstDollarExchangeRate = exchangeRateDAO.get("USD" + baseCurrencyCode);
        Optional<ExchangeRate> secondDollarExchangeRate = exchangeRateDAO.get("USD" + targetCurrencyCode);
        if (firstDollarExchangeRate.isPresent() && secondDollarExchangeRate.isPresent()) {
            return secondDollarExchangeRate.get().getRate() / firstDollarExchangeRate.get().getRate();
        }
        throw new NoDataFoundException("No exchange rate was found!");
    }

    private Double convertCurrency(Double amount, Double rate) {
        return amount * rate;
    }

}
