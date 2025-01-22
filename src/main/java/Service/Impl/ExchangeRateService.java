package Service.Impl;

import DAO.DataBaseSqlite;
import DAO.Impl.ExchangeRateDAO;
import DAO.Interfaces.UpdateDAO;
import Entity.Currency;
import Entity.ExchangeRate;
import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;
import Service.Interface.Service;
import Service.Interface.UpdatableService;

import java.util.List;

public class ExchangeRateService implements UpdatableService<String, ExchangeRate> {

    private final UpdateDAO<String, ExchangeRate> exchangeRateDAO = new ExchangeRateDAO(new DataBaseSqlite());
    private final Service<String, Currency> currencyService = new CurrencyService();

    @Override
    public List<ExchangeRate> readAll() throws DatabaseAccessException {
        return exchangeRateDAO.getAll();
    }

    @Override
    public ExchangeRate read(String codes) throws DatabaseAccessException, NoDataFoundException {
        return exchangeRateDAO.get(codes)
                .orElseThrow(() -> new NoDataFoundException("The exchange rate is missing from the database!"));
    }

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) throws DataDuplicationException, DatabaseAccessException, NoDataFoundException {
        Currency baseCurrency = currencyService.read(exchangeRate.getBaseCurrency().getCode());
        Currency targetCurrency = currencyService.read(exchangeRate.getTargetCurrency().getCode());

        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        return exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) throws DatabaseAccessException, NoDataFoundException {
        String codes = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
        ExchangeRate notUpdatedExchangeRate = read(codes);

        exchangeRate.setId(notUpdatedExchangeRate.getId());
        exchangeRate.setBaseCurrency(notUpdatedExchangeRate.getBaseCurrency());
        exchangeRate.setTargetCurrency(notUpdatedExchangeRate.getTargetCurrency());

        exchangeRateDAO.update(exchangeRate);
        return exchangeRate;
    }
}
