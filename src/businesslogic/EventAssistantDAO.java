package businesslogic;

import dataaccess.Connector;
import domain.EventAssistant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventAssistantDAO {

    private Connector connector = new Connector();

    public List<EventAssistant> displayAllEventAssistants() {

        String SQL_SELECT = "SELECT eMail, name FROM eventassistant";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        EventAssistant eventAssistant = null;
        List<EventAssistant> eventAssistantList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String eMail = resultSet.getString("eMail");
                String name = resultSet.getString("name");

                eventAssistant = new EventAssistant(eMail, name);
                eventAssistantList.add(eventAssistant);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return eventAssistantList;
    }

}
