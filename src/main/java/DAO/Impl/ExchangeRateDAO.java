package DAO.Impl;

import DAO.DataBaseSqlite;
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

public class ExchangeRateDAO implements UpdateDAO<String, ExchangeRate> {

    DataBaseSqlite dataBaseSqlite;
    ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    public ExchangeRateDAO(DataBaseSqlite dataBaseSqlite) {
        this.dataBaseSqlite = dataBaseSqlite;
    }

    @Override
    public List<ExchangeRate> getAll() throws DatabaseAccessException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = dataBaseSqlite.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT " +
                            "er.id AS id," +
                            "c.id AS base_currency_id, " +
                            "c.code AS base_currency_code, " +
                            "c.full_name AS base_currency_name, " +
                            "c.sign AS base_currency_sign, " +
                            "cu.id AS target_currency_id, " +
                            "cu.code AS target_currency_code, " +
                            "cu.full_name AS target_currency_name, " +
                            "cu.sign AS target_currency_sign, " +
                            "er.rate AS rate " +
                         "FROM exchange_rate AS er " +
                         "JOIN currencies AS c ON er.base_currency_id = c.id " +
                         "JOIN currencies AS cu ON er.target_currency_id = cu.id;"
            );
            while (resultSet.next()) {
                exchangeRates.add(exchangeRateMapper.toExchangeRate(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }

        return exchangeRates;
    }

    @Override
    public Optional<ExchangeRate> get(String codes) throws DatabaseAccessException {
        Optional<ExchangeRate> exchangeRateOptional = Optional.empty();

        try (Connection connection = dataBaseSqlite.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT " +
                                "er.id AS id," +
                                "c.id AS base_currency_id, " +
                                "c.code AS base_currency_code, " +
                                "c.full_name AS base_currency_name, " +
                                "c.sign AS base_currency_sign, " +
                                "cu.id AS target_currency_id, " +
                                "cu.code AS target_currency_code, " +
                                "cu.full_name AS target_currency_name, " +
                                "cu.sign AS target_currency_sign, " +
                                "er.rate AS rate " +
                            "FROM exchange_rate AS er " +
                            "JOIN currencies AS c ON er.base_currency_id = c.id " +
                            "JOIN currencies AS cu ON er.target_currency_id = cu.id " +
                            "WHERE c.code = ? AND cu.code =?;"
            );
            preparedStatement.setString(1, codes.substring(0, 3));
            preparedStatement.setString(2, codes.substring(3, 6));

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
    public Optional<ExchangeRate> getById(Integer id) throws DatabaseAccessException {
        Optional<ExchangeRate> exchangeRateOptional = Optional.empty();

        try (Connection connection = dataBaseSqlite.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT " +
                            "er.id AS id," +
                            "c.id AS base_currency_id, " +
                            "c.code AS base_currency_code, " +
                            "c.full_name AS base_currency_name, " +
                            "c.sign AS base_currency_sign, " +
                            "cu.id AS target_currency_id, " +
                            "cu.code AS target_currency_code, " +
                            "cu.full_name AS target_currency_name, " +
                            "cu.sign AS target_currency_sign, " +
                            "er.rate AS rate " +
                        "FROM exchange_rate AS er " +
                        "JOIN currencies AS c ON er.base_currency_id = c.id " +
                        "JOIN currencies AS cu ON er.target_currency_id = cu.id " +
                        "WHERE er.id = ?;"
            );
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

        try (Connection connection = dataBaseSqlite.getConnection()) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO exchange_rate(base_currency_id, target_currency_id, rate) VALUES(?, ?, ?)" +
                            "RETURNING id;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
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

        try (Connection connection = dataBaseSqlite.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE exchange_rate " +
                            "SET rate = ?" +
                            "WHERE id = ?;");
            preparedStatement.setDouble(1, exchangeRate.getRate());
            preparedStatement.setInt(2, exchangeRate.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseAccessException("Problems accessing the database!");
        }
    }
}
