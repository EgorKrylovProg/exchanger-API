package Controller.Serializer;

import Dto.ExchangeRate.ExchangeRateResp;

import java.util.List;

public class ExchangeRateSerializerToJson {

    public String serializeDto(ExchangeRateResp dto) {
        return dto.toString();
    }

    public String serializeListDto(List<ExchangeRateResp> listDto) {
        StringBuilder jsonStr = new StringBuilder("[");
        for (ExchangeRateResp dto: listDto) {
            jsonStr.append(dto).append(",");
        }
        jsonStr.delete(jsonStr.length() - 1, jsonStr.length()).append("]");
        return jsonStr.toString();
    }
}
