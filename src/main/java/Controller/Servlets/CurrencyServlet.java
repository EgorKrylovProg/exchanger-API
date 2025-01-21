package Controller.Servlets;

import Controller.Mapper.CurrencyMapper;
import Controller.Serializer.CurrencySerializerToJson;
import Dto.Currency.CurrencyReadingRequest;
import Dto.Currency.CurrencyReadingResponse;
import Entity.Currency;
import Exceptions.DatabaseAccessException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;
import Service.Impl.CurrencyService;
import Service.Interface.Service;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    Service<String, Currency> currencyService = new CurrencyService();
    CurrencyMapper mapper = new CurrencyMapper();
    CurrencySerializerToJson serializerToJson = new CurrencySerializerToJson();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {
            CurrencyReadingRequest currencyReadingRequest = mapper.toCurrencyReadingRequest(req);

            CurrencyReadingResponse currencyReadingResponse = mapper.toCurrencyReadingResponse(currencyService.read(currencyReadingRequest.getCode()));
            String jsonResponse = serializerToJson.serializeDto(currencyReadingResponse);

            writer.print(jsonResponse);
            resp.setStatus(200);
        } catch (IncorrectUrlException e) {
            writer.print(e);
            resp.setStatus(400);
        } catch (NoDataFoundException e) {
            writer.print(e);
            resp.setStatus(404);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        }
    }
}
