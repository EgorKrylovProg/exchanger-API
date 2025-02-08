package Controller.Servlets;

import Controller.Mapper.ExchangeRateMapper;
import Controller.Serializer.ExchangeRateSerializerToJson;
import Dto.ExchangeRate.ExchangeRateReadingRequest;
import Dto.ExchangeRate.ExchangeRateResp;
import Dto.ExchangeRate.ExchangeRateUpdatingRequest;
import Entity.ExchangeRate;
import Exceptions.DatabaseAccessException;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;
import Service.Impl.ExchangeRateService;
import Service.Interface.UpdatableService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    UpdatableService<String, ExchangeRate> service = new ExchangeRateService();
    ExchangeRateMapper mapper = new ExchangeRateMapper();
    ExchangeRateSerializerToJson serializer = new ExchangeRateSerializerToJson();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();


        try {

            ExchangeRateReadingRequest readingRequest = mapper.toReadingRequest(req);
            String currencyPairCodes = readingRequest.getCurrencyPairCodes();
            ExchangeRateResp dtoResp = mapper.toDtoResp(service.read(currencyPairCodes));

            String jsonResponse = serializer.serializeDto(dtoResp);
            writer.print(jsonResponse);
            resp.setStatus(200);
        } catch (IncorrectUrlException e) {
            writer.print(e);
            resp.setStatus(400);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } catch (NoDataFoundException e) {
            writer.print(e);
            resp.setStatus(404);
        } finally {
            writer.close();
        }

    }

    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();

        try {

            ExchangeRateUpdatingRequest updatingRequest = mapper.toUpdatingRequest(req);
            ExchangeRateResp dtoResp = mapper.toDtoResp(service.update(mapper.toEntity(updatingRequest)));
            String jsonResp = serializer.serializeDto(dtoResp);

            writer.print(jsonResp);
            resp.setStatus(200);
        } catch (IncorrectDataException | IncorrectUrlException e) {
            writer.print(e);
            resp.setStatus(400);
        } catch (NoDataFoundException e) {
            writer.print(e);
            resp.setStatus(404);
        } catch (DatabaseAccessException e) {
            writer.print(e);
            resp.setStatus(500);
        } finally {
            writer.close();
        }
    }


}
