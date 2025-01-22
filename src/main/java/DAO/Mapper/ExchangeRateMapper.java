package DAO.Mapper;

import Entity.Currency;
import Entity.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateMapper {

    public ExchangeRate toExchangeRate(ResultSet resultSet) throws SQLException {
        Currency baseCurrency = new Currency(
                resultSet.getInt("base_currency_id"),
                resultSet.getString("base_currency_code"),
                resultSet.getString("base_currency_name"),
                resultSet.getString("base_currency_sign")
        );
        Currency targetCurrency = new Currency(
                resultSet.getInt("target_currency_id"),
                resultSet.getString("target_currency_code"),
                resultSet.getString("target_currency_name"),
                resultSet.getString("target_currency_sign")
        );

        return new ExchangeRate(
                resultSet.getInt("id"),
                baseCurrency,
                targetCurrency,
                resultSet.getDouble("rate")
        );
    }
}
