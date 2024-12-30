package DAO.impl;

import DAO.DataBaseConnection;
import DAO.Interfaces.UpdateDAO;
import DAO.Mapper.ExchangeRateMapper;
import Entity.ExchangeRate;
import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO implements UpdateDAO<Integer, ExchangeRate> {

    DataBaseConnection dataBaseConnection;
    ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    public ExchangeRateDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<ExchangeRate> getAll() throws DatabaseAccessException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = dataBaseConnection.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM exchange_rate;");
            while (resultSet.next()) {
                exchangeRates.add(exchangeRateMapper.toExchangeRate(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }

        return exchangeRates;
    }

    @Override
    public Optional<ExchangeRate> get(Integer id) throws DatabaseAccessException {
        Optional<ExchangeRate> exchangeRateOptional = Optional.empty();

        try (Connection connection = dataBaseConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exchange_rate WHERE id = ?;");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exchangeRateOptional = Optional.of(exchangeRateMapper.toExchangeRate(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }
        return exchangeRateOptional;
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) throws DataDuplicationException, DatabaseAccessException, NoDataFoundException {

        try (Connection connection = dataBaseConnection.getConnection()) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO exchange_rate(base_currency_id, target_currency_id, rate) VALUES(?, ?, ?)" +
                            "RETURNING id;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, exchangeRate.getId());
            preparedStatement.setInt(2, exchangeRate.getBaseCurrencyId());
            preparedStatement.setDouble(3, exchangeRate.getRate());

            ResultSet resultSet = preparedStatement.executeQuery();
            exchangeRate.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            if (e.getErrorCode() == 19 && e.getMessage().toUpperCase().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                throw new DataDuplicationException("The record already exists!");
            }
            if (e.getErrorCode() == 19 && e.getMessage().toUpperCase().contains("SQLITE_CONSTRAINT_FOREIGNKEY")) {
                throw new NoDataFoundException("One (or both) currencies from a currency pair do not exist in the database!");
            }
            throw new DatabaseAccessException("Problems accessing the database!");
        }
        return exchangeRate;
    }

    @Override
    public void update(ExchangeRate exchangeRate) throws DatabaseAccessException {

        try (Connection connection = dataBaseConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE exchange_rate " +
                            "SET rate = ?" +
                            "WHERE id = ?;");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }
    }
}
