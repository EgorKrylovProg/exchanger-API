package Controller.Serializer;

import Dto.Currency.CurrencyResponse;

import java.util.List;

public class CurrencySerializerToJson {

    public String serializeDto(CurrencyResponse currencyReadingResponse) {
        return currencyReadingResponse.toString();
    }

    public String serializeListDto(List<CurrencyResponse> currenciesDto) {
        StringBuilder jsonCurrencies = new StringBuilder("[");
        for (CurrencyResponse dto: currenciesDto) {
            jsonCurrencies.append(dto);
            jsonCurrencies.append(",");
        }
        jsonCurrencies.delete(jsonCurrencies.length() - 1, jsonCurrencies.length()).append("]");

        return jsonCurrencies.toString();
    }

    
}
