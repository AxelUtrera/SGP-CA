package businesslogic;

import dataaccess.Connector;
import domain.Meet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeetDAO implements IMeetDAO{

    private Connector connector = new Connector();
    Connection connection = null;

    @Override
    public boolean addMeet(Meet meet){
        final String SQL_SENTENCE = "INSERT INTO meet(idMeet, Place, hour, date, subject, proyectName, idMemorandumMeet) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        boolean operationResult = false;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, "MET-"+String.valueOf(getLastIdMeetNumber()));
            statement.setString(2, meet.getPlaceMeet());
            statement.setTime(3, meet.getHourMeet());
            statement.setDate(4, meet.getDateMeet());
            statement.setString(5, meet.getSubject());
            statement.setString(6, meet.getProyectName());
            statement.setString(7,meet.getIdMemorandum());
            statement.executeUpdate();
            operationResult = true;

        }catch (SQLException ex){
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public List<Meet> displayAllMeets(){
        final String SQL_SENTENCE = "SELECT * FROM meet";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Meet meet = null;
        List<Meet> meetList = new ArrayList<>();

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                String idMeet = resultSet.getString("idMeet");
                Date dateMeet = resultSet.getDate("date");
                String subject = resultSet.getString("subject");
                Time hourMeet = resultSet.getTime("hour");
                String proyectName = resultSet.getString("proyectName");
                String placeMeet = resultSet.getString("place");
                String idMemorandum= resultSet.getString("idMemorandumMeet");

                meet = new Meet(idMeet,dateMeet,subject,hourMeet,proyectName,placeMeet,idMemorandum);
                meetList.add(meet);
            }
        }catch (SQLException ex){
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return meetList;
    }

    @Override
    public Meet findMeetById(String idMeetFind){
        String SQL_SENTENCE = "SELECT idMeet, date, subject, hour, proyectName, place, idMemorandumMeet FROM meet WHERE idMeet = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Meet meet = null;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, idMeetFind);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                String idMeet = resultSet.getString("idMeet");
                Date dateMeet = resultSet.getDate("date");
                String subject = resultSet.getString("subject");
                Time hour = resultSet.getTime("hour");
                String proyectName = resultSet.getString("proyectName");
                String placeMeet = resultSet.getString("place");
                String idMemorandum = resultSet.getString("idMemorandumMeet");

                meet = new Meet(idMeet,dateMeet,subject,hour,proyectName,placeMeet,idMemorandum);

            }
        }catch (SQLException ex){
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return meet;
    }

    @Override
    public boolean updateMeetById(Meet meet){
        final String SQL_SENTENCE = "UPDATE meet SET date=?, subject=?, hour=?, proyectName=?, place=?, idMemorandumMeet=? WHERE idMeet=?";
        PreparedStatement statement = null;
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setDate(1,meet.getDateMeet());
            statement.setString(2,meet.getSubject());
            statement.setTime(3,meet.getHourMeet());
            statement.setString(4,meet.getProyectName());
            statement.setString(5,meet.getPlaceMeet());
            statement.setString(6, meet.getIdMemorandum());
            statement.setString(7,meet.getIdMeet());
            statementResult = statement.executeUpdate();

            if(statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public boolean deleteMeetById(String idMeetToDelete){
        final String SQL_SENTENCE = "DELETE FROM meet WHERE idMeet = ?";
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,idMeetToDelete);
            statementResult = statement.executeUpdate();

            if(statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try{
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }


    public int getLastIdMeetNumber() {

        String SQL_SELECT = "SELECT convert(substring(idMeet, 5), UNSIGNED INTEGER) AS idMeet FROM meet ORDER BY idMeet DESC limit 1";
        Connection connection = null;
        int lastIdMeetNumber = 1;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastIdMeetNumber = resultSet.getInt("idMeet");
                lastIdMeetNumber++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MeetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lastIdMeetNumber;
    }


}
