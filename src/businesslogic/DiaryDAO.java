package businesslogic;

import dataaccess.Connector;
import domain.Diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiaryDAO implements IDiaryDAO{

    private Connector connector = new Connector();
    Connection connection = null;

    @Override
    public boolean addDiary(Diary diary){
        final String SQL_SENTENCE = "INSERT INTO diary(number, discussionLeader, realTime, estimatedTime, pointDiscussed, idMeet) VALUES (?,?,?,?,?,?)";
        boolean operationResult = false;
        PreparedStatement statement = null;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setInt(1,diary.getNumber());
            statement.setString(2,diary.getDiscussionLeader());
            statement.setInt(3, diary.getRealTime());
            statement.setInt(4,diary.getEstimatedTime());
            statement.setString(5,diary.getPointDiscussed());
            statement.setString(6,diary.getIdMeet());
            statement.executeUpdate();
            operationResult = true;

        }catch (SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        return operationResult;
    }

    @Override
    public List<Diary> displayAllDiaries(){
        final String SQL_SENTENCE = "SELECT * FROM diary";
        connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Diary diary = null;
        List<Diary> diaryList = new ArrayList<>();

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            resultSet =statement.executeQuery();

            while (resultSet.next()){
                int number = resultSet.getInt("number");
                String discussionLeader = resultSet.getString("discussionLeader");
                int realTime = resultSet.getInt("realTime");
                int estimatedTime = resultSet.getInt("realTime");
                String pointDiscussed = resultSet.getString("pointDiscussed");
                String idMeet = resultSet.getString("idMeet");

                diary = new Diary(number,discussionLeader, realTime, estimatedTime, pointDiscussed, idMeet);
                diaryList.add(diary);
            }
        }catch (SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return diaryList;
    }

    @Override
    public Diary findDiaryById(String idMeetFind){
        final String SQL_SENTENCE = "SELECT * FROM diary WHERE idMeet = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Diary diary = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,idMeetFind);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                int number = resultSet.getInt("number");
                String discussionLeader = resultSet.getString("discussionLeader");
                int realTime = resultSet.getInt("realTime");
                int estimatedTime = resultSet.getInt("estimatedTime");
                String pointDiscussed = resultSet.getString("pointDiscussed");
                String idMeet = resultSet.getString("idMeet");

                diary = new Diary(number,discussionLeader,realTime,estimatedTime,pointDiscussed,idMeet);

            }
        }catch (SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return diary;
    }

    @Override
    public boolean updateDiaryById(Diary diary){
        final String SQL_SENTENCE = "UPDATE diary SET discussionLeader=?, realTime=?, estimatedTime=?, pointDiscussed=? WHERE number = ?";
        PreparedStatement statement =null;
        int statementOperation = 0;
        boolean operationResult= false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,diary.getDiscussionLeader());
            statement.setInt(2,diary.getRealTime());
            statement.setInt(3, diary.getEstimatedTime());
            statement.setString(4, diary.getPointDiscussed());
            statement.setInt(5,diary.getNumber());
            statementOperation = statement.executeUpdate();

            if(statementOperation > OPERATIONOK) {
                operationResult = true;
            }

        }catch(SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public boolean deleteDiaryById(String idMeet){
        final String SQL_SENTENCE = "DELETE FROM diary WHERE idMeet = ?";
        connection = null;
        int statementOperation = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, idMeet);
            statementOperation = statement.executeUpdate();

            if(statementOperation > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    public int getMaxNumberOfDiaries(){
        final String  SQL_SENTENCE = "SELECT MAX(number) FROM diary";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int maxNumber = 0;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                maxNumber = resultSet.getInt("MAX(number)");
            }

        }catch (SQLException ex){
            Logger.getLogger(DiaryDAO.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try{
                connector.closeConnection(connection);
            }catch (SQLException ex){

            }
        }
        return maxNumber;
    }


}
