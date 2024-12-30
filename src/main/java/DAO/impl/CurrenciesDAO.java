package DAO.impl;

import DAO.DataBaseConnection;
import DAO.Interfaces.DAO;
import DAO.Mapper.CurrencyMapper;
import Entity.Currency;
import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrenciesDAO implements DAO<Integer, Currency> {

    DataBaseConnection dataBaseConnection;
    CurrencyMapper currencyMapper = new CurrencyMapper();

    public CurrenciesDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<Currency> getAll() throws DatabaseAccessException {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = dataBaseConnection.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM currencies;");
            while (resultSet.next()) {
                currencies.add(currencyMapper.toCurrency(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }

        return currencies;
    }

    @Override
    public Optional<Currency> get(Integer id) throws DatabaseAccessException {
        Optional<Currency> currencyOptional = Optional.empty();

        try (Connection connection = dataBaseConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currencies WHERE id = ?;");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currencyOptional = Optional.of(currencyMapper.toCurrency(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }

        return currencyOptional;
    }

    @Override
    public Currency save(Currency currency) throws DataDuplicationException, DatabaseAccessException {

        try (Connection connection = dataBaseConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO currencies(code, full_name, sign) " +
                    "VALUES(?, ?, ?) RETURNING id;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());

            currency.setId(preparedStatement.executeQuery().getInt("id"));
        } catch (SQLException e) {
            if (e.getErrorCode() == 19 && e.getMessage().toUpperCase().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                throw new DataDuplicationException("The record already exists!");
            }
            throw new DatabaseAccessException("Problems accessing the database!");
        }
        return currency;
    }
}
