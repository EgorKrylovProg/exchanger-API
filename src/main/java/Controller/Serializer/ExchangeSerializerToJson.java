package Controller.Serializer;

import Dto.Exchange.ExchangeResponse;

public class ExchangeSerializerToJson {

    public String serializeDto(ExchangeResponse response){
        return response.toString();
    }
}
