package Controller.Serializer;

import Dto.ExchangeRate.ExchangeRateReadingResp;

import java.util.List;

public class ExchangeRateSerializerToJson {

    public String serializeDto(ExchangeRateReadingResp dto) {
        return dto.toString();
    }

    public String serializeListDto(List<ExchangeRateReadingResp> listDto) {
        StringBuilder jsonStr = new StringBuilder("[");
        for (ExchangeRateReadingResp dto: listDto) {
            jsonStr.append(dto).append(",");
        }
        jsonStr.delete(jsonStr.length() - 1, jsonStr.length()).append("]");
        return jsonStr.toString();
    }
}
