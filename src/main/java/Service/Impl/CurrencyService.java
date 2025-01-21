package Service.Impl;

import DAO.Impl.CurrenciesDAO;
import DAO.Interfaces.DAO;
import Entity.Currency;
import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;
import Service.Interface.Service;
import DAO.DataBaseSqlite;

import java.util.List;

public class CurrencyService implements Service<String, Currency> {

    private final DAO<String, Currency> currencyDAO = new CurrenciesDAO(new DataBaseSqlite());


    @Override
    public List<Currency> readAll() throws DatabaseAccessException {
        return currencyDAO.getAll();
    }

    @Override
    public Currency read(String code) throws DatabaseAccessException, NoDataFoundException{
        return currencyDAO.get(code)
                .orElseThrow(() -> new NoDataFoundException("The currency is missing from the database!"));
    }

    @Override
    public Currency create(Currency currency) throws DataDuplicationException, DatabaseAccessException, NoDataFoundException {
        return currencyDAO.save(currency);
    }
}
