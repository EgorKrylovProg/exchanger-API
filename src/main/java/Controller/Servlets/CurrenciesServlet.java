package Controller.Servlets;

import Controller.Serializer.CurrencySerializerToJson;
import Dto.Currency.CurrencyCreatingRequest;
import Dto.Currency.CurrencyReadingResponse;
import Entity.Currency;
import Exceptions.*;
import Service.Impl.CurrencyService;
import Service.Interface.Service;
import Controller.Mapper.CurrencyMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final Service<String, Currency> currencyService = new CurrencyService();
    private final CurrencySerializerToJson serializerToJson = new CurrencySerializerToJson();
    private final CurrencyMapper mapper = new CurrencyMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {

            List<CurrencyReadingResponse> currenciesDto = currencyService.readAll().stream().map(mapper::toCurrencyReadingResponse).toList();
            String jsonResponse = serializerToJson.serializeListDto(currenciesDto);

            writer.print(jsonResponse);
            resp.setStatus(200);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } finally {
            writer.close();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {

            CurrencyCreatingRequest currencyForCreating = mapper.toCurrencyCreatingRequest(req);
            Currency currency = currencyService.create(mapper.toCurrency(currencyForCreating));
            String jsonResponse = serializerToJson.serializeDto(mapper.toCurrencyReadingResponse(currency));

            writer.print(jsonResponse);
            resp.setStatus(201);
        } catch (IncorrectDataException | NoDataFoundException e) {
            writer.print(e);
            resp.setStatus(400);
        } catch (DataDuplicationException e) {
            writer.print(e);
            resp.setStatus(409);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } finally {
            writer.close();
        }
    }
}
