package Controller.Servlets;

import Controller.Mapper.ExchangeRateMapper;
import Controller.Serializer.ExchangeRateSerializerToJson;
import Dto.ExchangeRate.ExchangeRateCreatingRequest;
import Dto.ExchangeRate.ExchangeRateResp;
import Entity.ExchangeRate;
import Exceptions.*;
import Service.Impl.ExchangeRateService;
import Service.Interface.Service;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    Service<String, ExchangeRate> service = new ExchangeRateService();
    ExchangeRateMapper mapper = new ExchangeRateMapper();
    ExchangeRateSerializerToJson serializer = new ExchangeRateSerializerToJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {

            List<ExchangeRateResp> exchangeRates = service.readAll()
                    .stream()
                    .map(mapper::toDtoResp)
                    .toList();
            String jsonResp = serializer.serializeListDto(exchangeRates);

            writer.print(jsonResp);
            resp.setStatus(200);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {

            ExchangeRateCreatingRequest creatingRequest = mapper.toCreatingRequest(req);
            ExchangeRateResp dtoResp = mapper.toDtoResp(service.create(mapper.toEntity(creatingRequest)));
            String jsonResponse = serializer.serializeDto(dtoResp);

            writer.print(jsonResponse);
            resp.setStatus(201);
        } catch (NoDataFoundException e) {
            writer.print(e);
            resp.setStatus(404);
        } catch (DataDuplicationException e) {
            writer.print(e);
            resp.setStatus(409);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } catch (IncorrectUrlException | IncorrectDataException e) {
            writer.print(e);
            resp.setStatus(400);
        } finally {
            writer.close();
        }
    }
}
