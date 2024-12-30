package DAO.Mapper;

import Entity.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateMapper {

    public ExchangeRate toExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getInt("id"),
                resultSet.getInt("base_currency_id"),
                resultSet.getInt("target_currency_id"),
                resultSet.getDouble("rate")
        );
    }
}
