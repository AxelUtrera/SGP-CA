package businesslogic;

import dataaccess.Connector;
import domain.Prerequirement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrerequirementDAO implements IPrerequirementDAO{
    Connector connector = new Connector();
    Connection connection = null;

    @Override
    public boolean addPrerequirement(Prerequirement prerequirement){
        final String SQL_SENTENCE = "INSERT INTO prerequirement(idPrerequirement, description, idMember, idMeet) VALUES (?,?,?,?)";
        PreparedStatement statement = null;
        boolean operationResult = false;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,"PRE-"+String.valueOf(getLastIdPrerequirementNumber()));
            statement.setString(2,prerequirement.getDescription());
            statement.setString(3, prerequirement.getIdMember());
            statement.setString(4,prerequirement.getIdMeet());
            statement.executeUpdate();
            operationResult = true;
        }catch (SQLException ex){
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public List<Prerequirement> displayAllPrerequirements(){
        final String SQL_SENTENCE = "SELECT * FROM prerequirement";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Prerequirement prerequirement = null;
        List<Prerequirement> prerequirementsList = new ArrayList<>();

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                String idPrerequirement = resultSet.getString("idPrerequirement");
                String description = resultSet.getString("description");
                String idMember = resultSet.getString("idMember");
                String idMeet = resultSet.getString("idMeet");

                prerequirement = new Prerequirement(idPrerequirement,description, idMember,idMeet);
                prerequirementsList.add(prerequirement);
            }

        }catch (SQLException ex){
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return prerequirementsList;
    }

    public Prerequirement findPrerequirementById(String idPrerequirementFind){
        final String SQL_SENTENCE = "SELECT * FROM prerequirement WHERE idPrerequirement = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Prerequirement prerequirement = null;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, idPrerequirementFind);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                String idPrerequirement = resultSet.getString("idPrerequirement");
                String description = resultSet.getString("description");
                String idMember = resultSet.getString("idMember");
                String idMeet = resultSet.getString("idMeet");

                prerequirement = new Prerequirement(idPrerequirement, description,idMember, idMeet);
            }
        }catch (SQLException ex){
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return prerequirement;
    }

    @Override
    public boolean updatePrerequirement(Prerequirement prerequirement){
        final String SQL_SENTENCE = "UPDATE prerequirement SET description = ?, idMember = ? WHERE  idPrerequirement = ?";
        PreparedStatement statement = null;
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1, prerequirement.getDescription());
            statement.setString(2,prerequirement.getIdMember());
            statement.setString(3,prerequirement.getIdPrerequirement());
            statementResult = statement.executeUpdate();

            if (statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    @Override
    public boolean deletePrerequirementById(String idPrerequirement){
        final String SQL_SENTENCE = "DELETE FROM prerequirement WHERE idPrerequirement = ?";
        PreparedStatement statement = null;
        int statementResult = 0;
        boolean operationResult = false;
        final int OPERATIONOK = 0;

        try{
            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SENTENCE);
            statement.setString(1,idPrerequirement);
            statementResult = statement.executeUpdate();

            if (statementResult > OPERATIONOK){
                operationResult = true;
            }

        }catch (SQLException ex){
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.closeConnection(connection);
            }catch (SQLException ex){
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }

    public int getLastIdPrerequirementNumber() {

        String SQL_SELECT = "SELECT convert(substring(idPrerequirement, 5), UNSIGNED INTEGER) AS idPrerequirement FROM prerequirement ORDER BY idPrerequirement DESC limit 1";
        Connection connection = null;
        int lastIdPrerequirementNumber = 1;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastIdPrerequirementNumber = resultSet.getInt("idPrerequirement");
                lastIdPrerequirementNumber++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrerequirementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lastIdPrerequirementNumber;
    }


}
