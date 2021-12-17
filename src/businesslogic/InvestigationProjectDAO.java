package businesslogic;

import dataaccess.Connector;
import domain.InvestigationProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvestigationProjectDAO implements IInvestigationProjectDAO {

    private final Connector CONNECTOR = new Connector();

    public List<InvestigationProject> displayAllinvestigationProjects() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM investigationProject";
        Connection connection = null;
        InvestigationProject investigationProject;
        List<InvestigationProject> investigationProjectList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idInvestigationProject = resultSet.getString("idInvestigationProject");
                String title = resultSet.getString("title");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String description = resultSet.getString("description");


                investigationProject = new InvestigationProject(idInvestigationProject, title, startDate, endDate, description);
                investigationProjectList.add(investigationProject);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return investigationProjectList;
    }

    public boolean addInvestigationProject(InvestigationProject investigationProject) throws BusinessLogicException{


        final String SQL_INSERT = "INSERT INTO investigationProject (`idInvestigationProject`, `title`, `startDate`," +
                " `endDate`, `description`) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;
        String idInvestigationProject = "IVP-"+getLastIdInvestigationProject();


        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idInvestigationProject);
            statement.setString(2, investigationProject.getTitle());
            statement.setDate( 3, (Date) investigationProject.getStartDate());
            statement.setDate( 4, (Date) investigationProject.getEndDate());
            statement.setString(5, investigationProject.getDescription());

            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }


    public boolean updateInvestigationProject(InvestigationProject investigationProject) throws BusinessLogicException{


        final String SQL_UPDATE = "UPDATE investigationProject SET title=?, startDate=?, endDate=?, description=?  WHERE idInvestigationProject=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, investigationProject.getTitle());
            statement.setDate( 2, (Date) investigationProject.getStartDate());
            statement.setDate( 3, (Date) investigationProject.getEndDate());
            statement.setString(4, investigationProject.getDescription());
            statement.setString(5, investigationProject.getIdInvestigationProject());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteInvestigationProject(String idInvestigationProject) throws BusinessLogicException{


        final String SQL_DELETE = "DELETE FROM investigationProject WHERE idInvestigationProject=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idInvestigationProject);
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public InvestigationProject foundInvestigationProjectByidInvestigationProject(String idInvestigationproject) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM investigationProject WHERE idInvestigationProject = ?";
        Connection connection = null;
        InvestigationProject investigationProject = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idInvestigationproject);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idInvestigationProject = resultSet.getString("idInvestigationProject");
                String title = resultSet.getString("title");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String description = resultSet.getString("description");

                investigationProject = new InvestigationProject(idInvestigationProject, title, startDate, endDate, description);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return investigationProject;
    }

    public InvestigationProject foundInvestigationProjectByTitle(String titlee) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM investigationProject WHERE title = ?";
        Connection connection = null;
        InvestigationProject investigationProject = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, titlee);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idInvestigationProject = resultSet.getString("idInvestigationProject");
                String title = resultSet.getString("title");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String description = resultSet.getString("description");

                investigationProject = new InvestigationProject(idInvestigationProject, title, startDate, endDate, description);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return investigationProject;
    }

    public int getLastIdInvestigationProject() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT idInvestigationProject FROM investigationProject";
        Connection connection = null;
        List<String> idInvestigationProjectsStringList = new ArrayList<>();
        List<Integer> idInvestigationProjectsIntList = new ArrayList<>();
        int maxId=1;
        int idInvestigationProjectInt;
        StringBuilder idInvestigationProjectString = new StringBuilder();
        String idInvestigationProjectResult ="";

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                idInvestigationProjectResult = resultSet.getString("idInvestigationProject");
                idInvestigationProjectsStringList.add(idInvestigationProjectResult);
            }

            if(!idInvestigationProjectsStringList.isEmpty()){
                for(String idInvestigationProject : idInvestigationProjectsStringList){
                    idInvestigationProjectString = new StringBuilder();
                    for(int i = 4; i< idInvestigationProject.length(); i++){
                        idInvestigationProjectString.append(String.valueOf(idInvestigationProject.charAt(i)));
                    }
                    idInvestigationProjectInt = Integer.parseInt(idInvestigationProjectString.toString());
                    idInvestigationProjectsIntList.add(idInvestigationProjectInt);
                }
                maxId = idInvestigationProjectsIntList.get(0);
                for(int idInvestigationProjectINT : idInvestigationProjectsIntList){
                    if(idInvestigationProjectINT > maxId){
                        maxId = idInvestigationProjectINT;
                    }
                }

                maxId++;
            }

        } catch (SQLException sqlException) {
            throw new BusinessLogicException("ConnectionException", sqlException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(InvestigationProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return maxId;
    }


}


