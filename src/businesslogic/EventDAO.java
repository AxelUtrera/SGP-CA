package businesslogic;

import static dataaccess.Connector.*;
import dataaccess.Connector;
import domain.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDAO implements IEventDAO{


    public void insertOneEvent(Event event){

        final String SQL_INSERT = "INSERT INTO academicevent(idAcademicEvent, title, type, date, hour, place) VALUES(?,?,?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try{

            connection = getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString( 1, event.getIdAcademicEvent());
            statement.setString(2, event.getTitle());
            statement.setString(3, event.getType());
            statement.setDate( 4, event.getEventDate());
            statement.setTime(5, event.getHour());
            statement.setString(6, event.getPlace());
            statement.executeUpdate();

        }catch (SQLException ex){
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                close(statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Event> displayAllEvents(){

        final String SQL_SELECT = "SELECT * FROM academicevent";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Event event = null;
        List<Event> eventList = new ArrayList<>();

        try{
            connection = getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                String idAcademicEvent = resultSet.getString("idAcademicEvent");
                String title = resultSet.getString("title");
                String type = resultSet.getString("type");
                Time hour = resultSet.getTime("hour");
                String place = resultSet.getString("place");
                Date eventDate = resultSet.getDate("date");

                event = new Event(idAcademicEvent, title,type, hour,place, eventDate);
                eventList.add(event);
            }
        }catch (SQLException ex){
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try{
                closeSelect(resultSet,statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return eventList;
    }

    public boolean foundEventById(String idEvent){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlSelect = "SELECT idAcademicEvent, title, type, date, hour, place FROM academicevent WHERE idAcademicEvent = ?";
        Event event = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sqlSelect);
            statement.setString(1, idEvent);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                String idAcademicEvent = resultSet.getString("idAcademicEvent");
                String title = resultSet.getString("title");
                String type = resultSet.getString("type");
                Time hour = resultSet.getTime("hour");
                String place = resultSet.getString("place");
                Date date = resultSet.getDate("date");

                event = new Event(idAcademicEvent, title, type, hour, place, date);

            }
        }catch (SQLException ex){
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try{
                closeSelect(resultSet, statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public boolean deleteEventById(Event event){

        String sqlDelete = "DELETE FROM academicevent WHERE idAcademicEvent = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try{

            connection = getConnection();
            statement = connection.prepareStatement(sqlDelete);
            statement.setString(1,event.getIdAcademicEvent());
            statement.executeUpdate();

        }catch (SQLException ex){
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try{
                statement.close();
                connection.close();
            }catch (SQLException ex){
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
}
