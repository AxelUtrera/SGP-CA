package businesslogic;

import static dataaccess.Connector.*;

import dataaccess.Connector;
import domain.Meet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MeetDAO implements IMeetDAO{

    private static final String SQL_INSERT = "INSERT INTO meet(idMeet, Place, hour, date, subject, proyectName, idMemorandumMeet) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM meet";

    public void insertOneMeet(Meet meet){
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, meet.getIdMeet());
            statement.setString(2, meet.getPlaceMeet());
            statement.setTime(3, meet.getHourMeet());
            statement.setDate(4, meet.getDateMeet());
            statement.setString(5, meet.getSubject());
            statement.setString(6, meet.getProyectName());

        }catch (SQLException ex){
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                close(statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Meet> displayAllMeets(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Meet meet = null;
        List<Meet> meetList = new ArrayList<>();

        try{
            connection = getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                String idMeet = resultSet.getString("idMeet");
                Date dateMeet = resultSet.getDate("date");
                String subject = resultSet.getString("subject");
                Time hourMeet = resultSet.getTime("hour");
                String proyectName = resultSet.getString("proyectName");
                String placeMeet = resultSet.getString("place");

                //tengo una duda acerca de como
                //importar la minuta completa ohacia aqui, debo pensarlo.

                meet = new Meet(idMeet,dateMeet,subject,hourMeet,proyectName,placeMeet);
                meetList.add(meet);
            }
        }catch (SQLException ex){
            Logger.getLogger(Meet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                closeSelect(resultSet,statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Meet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return meetList;
    }

    public int foundMeetById(String idMeet){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM meet WHERE idMeet = ?";

        try{
            connection = getConnection();
            statement = connection.prepareStatement(sqlSelect);
            statement.setString(1,"idMeet");
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException ex){
            Logger.getLogger(Meet.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                closeSelect(resultSet,statement);
                close(connection);
            }catch (SQLException ex){
                Logger.getLogger(Meet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
}
