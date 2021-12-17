package businesslogic;

import dataaccess.Connector;
import domain.Memorandum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemorandumDAO implements IMemorandumDAO{
    Connector connector = new Connector();
    Connection connection = null;

    @Override
    public boolean addMemorandum(Memorandum memorandum){
        final String SQL_SENTENCE = "INSERT INTO memorandummeet(idMemorandumMeet, hour, place, date, subject) VALUES (?,?,?,?,?)";
        connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean operationResult = false;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, memorandum.getIdMemorandum());
            statement.setTime(2, memorandum.getHour());
            statement.setString(3, memorandum.getPlace());
            statement.setDate(4, memorandum.getDate());
            statement.setString(5,memorandum.getSubject());
            statement.executeUpdate();
            operationResult = true;

        }catch (SQLException ex){
            Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public List<Memorandum> displayAllMemorandums() {
        final String SQL_SENTENCE = "SELECT * FROM memorandummeet";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Memorandum memorandum = null;
        List<Memorandum> memorandumList = new ArrayList<>();

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idMemorandum = resultSet.getString("idMemorandumMeet");
                String subject = resultSet.getString("subject");
                Date date = resultSet.getDate("date");
                Time hour = resultSet.getTime("hour");
                String place = resultSet.getString("place");

                memorandum = new Memorandum(idMemorandum, hour, place, date, subject);
                memorandumList.add(memorandum);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return memorandumList;
    }

    @Override
    public Memorandum findMemorandumById(String idMemorandumFind){
        String SQL_SENTENCE = "SELECT * FROM memorandumMeet WHERE idMemorandumMeet = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Memorandum memorandum = null;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, idMemorandumFind);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                String idmemorandum = resultSet.getString("idMemorandumMeet");
                Time hour = resultSet.getTime("hour");
                String place = resultSet.getString("place");
                Date date = resultSet.getDate("date");
                String subject = resultSet.getString("subject");

                memorandum = new Memorandum(idmemorandum,hour,place,date,subject);
            }
        }catch (SQLException ex){
            Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return memorandum;
    }


    @Override
    public boolean deleteMemorandumById(String idMemorandum){
        final String SQL_SENTENCE = "DELETE FROM memorandummeet WHERE idMemorandumMeet = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection  = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,idMemorandum);
            statementResult = statement.executeUpdate();

            if (statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try{
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(MemorandumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

}


