package Service.Impl;

import DAO.DataBaseSqlite;
import DAO.Impl.ExchangeRateDAO;
import DAO.Interfaces.UpdateDAO;
import Entity.ExchangeRate;
import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;
import Service.Interface.UpdatableService;

import java.util.List;

public class ExchangeRateService implements UpdatableService<String, ExchangeRate> {

    private final UpdateDAO<String, ExchangeRate> exchangeRateDAO = new ExchangeRateDAO(new DataBaseSqlite());

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
        return exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) throws DatabaseAccessException, NoDataFoundException {
        exchangeRateDAO.update(exchangeRate);
        return exchangeRateDAO.getById(exchangeRate.getId())
                .orElseThrow(() -> new NoDataFoundException("The exchange rate with this id is not in the database!"));
    }
}
