package businesslogic;

import dataaccess.Connector;
import domain.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO{

    private final Connector CONNECTOR = new Connector();
    private Connection connection = null;

    @Override
    public boolean addEvent(Event event) throws BusinessLogicException{
        final String SQL_SENTENCE = "INSERT INTO academicevent(idAcademicEvent, title, type, date, hour, place) VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = null;
        boolean operationResult = false;

        try{

            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString( 1, "EVT-"+String.valueOf(getLastIdEventNumber()));
            statement.setString(2, event.getTitle());
            statement.setString(3, event.getType());
            statement.setDate( 4, event.getEventDate());
            statement.setTime(5, event.getHour());
            statement.setString(6, event.getPlace());
            statement.executeUpdate();
            operationResult = true;

        }catch (SQLException sqlException){
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            }catch (SQLException sqlException){
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return operationResult;
    }

    @Override
    public List<Event> displayAllEvents() throws BusinessLogicException{
        final String SQL_SENTENCE = "SELECT * FROM academicevent";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Event event = null;
        List<Event> eventList = new ArrayList<>();

        try{
            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
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
        }catch (SQLException sqlException){
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try{
                CONNECTOR.closeConnection(connection);
            }catch (SQLException sqlException){
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return eventList;
    }

    @Override
    public Event findEventById(String idEvent) throws BusinessLogicException{
        final String SQL_SENTENCE= "SELECT idAcademicEvent, title, type, date, hour, place FROM academicevent WHERE idAcademicEvent = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Event event = null;

        try {
            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
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
        }catch (SQLException sqlException){
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try{
                CONNECTOR.closeConnection(connection);
            }catch (SQLException sqlException){
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return event;
    }

    @Override
    public boolean updateEventById(Event event) throws BusinessLogicException{
        final String SQL_SENTENCE = "UPDATE academicevent SET title=?, type=?, hour=?, place=?, date=? WHERE idAcademicEvent=?";
        PreparedStatement statement = null;
        boolean operationResult = false;
        int statementOperation = 0;
        final int OPERATIONOK = 0;

        try{
            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getType());
            statement.setTime(3, event.getHour());
            statement.setString(4, event.getPlace());
            statement.setDate(5,event.getEventDate());
            statement.setString(6,event.getIdAcademicEvent());
            statementOperation = statement.executeUpdate();

            if(statementOperation > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException sqlException){
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            }catch (SQLException sqlException){
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return operationResult;
    }

    @Override
    public boolean deleteEventById(String idAcademicEvent) throws BusinessLogicException{
        final String SQL_SENTENCE = "DELETE FROM academicevent WHERE idAcademicEvent = ?";
        PreparedStatement statement = null;
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{

            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, idAcademicEvent);
            statementResult = statement.executeUpdate();

            if(statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException sqlException){
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try{
                CONNECTOR.closeConnection(connection);
            }catch (SQLException sqlException){
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return operationResult;
    }

    public int getLastIdEventNumber() throws BusinessLogicException{

        String SQL_SELECT = "SELECT convert(substring(idAcademicEvent, 5), UNSIGNED INTEGER) AS idAcademicEvent " +
                "FROM academicevent ORDER BY idAcademicEvent DESC limit 1";
        Connection connection = null;
        int lastIdEventNumber = 1;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastIdEventNumber = resultSet.getInt("idAcademicEvent");
                lastIdEventNumber++;
            }

        } catch (SQLException sqlException) {
            throw new BusinessLogicException("ConnectionException", sqlException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException sqlException) {
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }
        return lastIdEventNumber;
    }



}
