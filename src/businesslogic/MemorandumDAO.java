package businesslogic;

import static dataaccess.Connector.*;

import dataaccess.Connector;
import domain.Memorandum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemorandumDAO implements IMemorandumDAO{

    private static final String SQL_SELECT = "SELECT idMemorandmMeet, subject, date, hour, place FROM memorandummeet";

    public List<Memorandum> displayAllMemorandums() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Memorandum memorandum = null;
        List<Memorandum> memorandumList = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
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
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                closeSelect(resultSet, statement);
                close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return memorandumList;
    }

    public int foundMemorandumById(String idMemorandum){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM memorandumMeet WHERE idMemorandumMeet = ?";

        try{
            connection = getConnection();
            statement = connection.prepareStatement(sqlSelect);
            statement.setString(1, idMemorandum);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException ex){
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                closeSelect(resultSet, statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }


}


