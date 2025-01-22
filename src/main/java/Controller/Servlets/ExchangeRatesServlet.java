package Controller.Servlets;

import Controller.Mapper.ExchangeRateMapper;
import Controller.Serializer.ExchangeRateSerializerToJson;
import Dto.ExchangeRate.ExchangeRateReadingResp;
import Entity.ExchangeRate;
import Exceptions.DatabaseAccessException;
import Service.Impl.ExchangeRateService;
import Service.Interface.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    Service<String, ExchangeRate> exchangeRateService = new ExchangeRateService();
    ExchangeRateMapper mapper = new ExchangeRateMapper();
    ExchangeRateSerializerToJson serializer = new ExchangeRateSerializerToJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {

            List<ExchangeRateReadingResp> exchangeRates = exchangeRateService.readAll()
                    .stream()
                    .map(mapper::toExchangeRateReadingResp)
                    .toList();
            String jsonResp = serializer.serializeListDto(exchangeRates);

            writer.print(jsonResp);
            resp.setStatus(200);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        }
    }
}
