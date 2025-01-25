package Service.Impl;

import DAO.DataBaseSqlite;
import DAO.Impl.ExchangeRateDAO;
import DAO.Interfaces.UpdateDAO;
import Entity.ExchangeRate;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.util.Optional;

public class ExchangerService {

    UpdateDAO<String, ExchangeRate> exchangeRateDAO = new ExchangeRateDAO(new DataBaseSqlite());

    public Double exchange(String baseCurrencyCode, String targetCurrencyCode, Double amount) throws NoDataFoundException, DatabaseAccessException {
        return getRate(baseCurrencyCode, targetCurrencyCode) * amount;
    }

    public Double getRate(String baseCurrencyCode, String targetCurrencyCode) throws DatabaseAccessException, NoDataFoundException {

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
}
