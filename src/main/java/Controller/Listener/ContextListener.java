package Controller.Listener;

import DAO.ConnectionPoolSqlite;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebListener
public class ContextListener implements ServletContextListener {

    private final ConnectionPoolSqlite connectionPoolSqlite = new ConnectionPoolSqlite();
    private Context context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            context = new InitialContext();

            context.rebind("baseDataSource", connectionPoolSqlite.getBaseDataSource());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (context != null) {
                context.unbind("baseDataSource");
                context.close();
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
