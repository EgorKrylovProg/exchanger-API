package Controller.Servlets;

import Controller.Mapper.ExchangeMapper;
import Controller.Serializer.ExchangeSerializerToJson;
import Dto.Exchange.ExchangeRequest;
import Dto.Exchange.ExchangeResponse;
import Exceptions.DatabaseAccessException;
import Exceptions.IncorrectDataException;
import Exceptions.IncorrectUrlException;
import Exceptions.NoDataFoundException;
import Service.Impl.ExchangerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    ExchangeMapper mapper = new ExchangeMapper();
    ExchangerService service = new ExchangerService();
    ExchangeSerializerToJson serializer = new ExchangeSerializerToJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var writer = resp.getWriter();
        resp.setContentType("json");

        try {
            ExchangeRequest exchangeRequest = mapper.toRequest(req);
            ExchangeResponse exchangeResponse = mapper.toResponse(service.exchangeCurrency(exchangeRequest));

            writer.print(serializer.serializeDto(exchangeResponse));
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
        }
    }
}
