package Controller.Serializer;

import Dto.Currency.CurrencyReadingResponse;

import java.util.List;

public class CurrencySerializerToJson {

    public String serializeDto(CurrencyReadingResponse currencyReadingResponse) {
        return currencyReadingResponse.toString();
    }

    public String serializeListDto(List<CurrencyReadingResponse> currenciesDto) {
        StringBuilder jsonCurrencies = new StringBuilder("[");
        for (CurrencyReadingResponse dto: currenciesDto) {
            jsonCurrencies.append(dto);
            jsonCurrencies.append(",");
        }
        jsonCurrencies.delete(jsonCurrencies.length() - 1, jsonCurrencies.length()).append("]");

        return jsonCurrencies.toString();
    }

    
}
