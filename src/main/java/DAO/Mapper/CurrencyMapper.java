package DAO.Mapper;

import Entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public Currency toCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("full_name"),
                resultSet.getString("sign")
        );
    }

}
