package businesslogic;

import dataaccess.Connector;
import domain.ReceptionWork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionWorkDAO implements IReceptionWorkDAO{

    private final Connector CONNECTOR = new Connector();

    public List<ReceptionWork> displayAllReceptionWorks() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM receptionwork";
        Connection connection = null;
        ReceptionWork receptionWork;
        List<ReceptionWork> receptionWorkList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idReceptionWork = resultSet.getString("idReceptionWork");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String condition = resultSet.getString("condition");
                String studentName = resultSet.getString("studentName");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                receptionWork = new ReceptionWork (type, title,academicDegree, idInvestigationProject, idReceptionWork, condition, studentName);
                receptionWorkList.add(receptionWork);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return receptionWorkList;
    }

    public boolean addReceptionWork(ReceptionWork receptionWork) throws BusinessLogicException{


        final String SQL_INSERT = "INSERT INTO receptionwork (`idReceptionWork`, `type`, `title`, `condition`, `studentName`, `academicDegree`, `idInvestigationProject`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;
        String id = "RPW-"+getLastId();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, id);
            statement.setString(2, receptionWork.getType());
            statement.setString( 3, receptionWork.getTitle());
            statement.setString(4, receptionWork.getCondition());
            statement.setString(5, receptionWork.getStudentName());
            statement.setString(6, receptionWork.getAcademicdegree());
            statement.setString(7, receptionWork.getIdInvestigationProject());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }


    public boolean updateReceptionWork(ReceptionWork receptionWork) throws BusinessLogicException{


        final String SQL_UPDATE = "UPDATE receptionwork SET idReceptionWork=?, `type`=?, title=?, `condition`=?, studentName=?, academicDegree=?, idInvestigationProject=? WHERE idReceptionWork=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, receptionWork.getIdReceptionWork());
            statement.setString(2, receptionWork.getType());
            statement.setString( 3, receptionWork.getTitle());
            statement.setString(4, receptionWork.getCondition());
            statement.setString(5, receptionWork.getStudentName());
            statement.setString(6, receptionWork.getAcademicdegree());
            statement.setString(7, receptionWork.getIdInvestigationProject());
            statement.setString(8, receptionWork.getIdReceptionWork());
            statement.executeUpdate();
            operationResult = true;
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteReceptionWork(String idReceptionWork) throws BusinessLogicException{


        final String SQL_DELETE = "DELETE FROM receptionwork WHERE idReceptionWork=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idReceptionWork);
            statement.executeUpdate();
            operationResult = true;


        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public ReceptionWork foundReceptionWorkByID(String idReceptionwork) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM receptionwork WHERE idReceptionWork = ?";
        Connection connection = null;
        ReceptionWork receptionWork = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idReceptionwork);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idReceptionWork = resultSet.getString("idReceptionWork");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String condition = resultSet.getString("condition");
                String studentName = resultSet.getString("studentName");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                receptionWork = new ReceptionWork (type, title,academicDegree, idInvestigationProject, idReceptionWork, condition, studentName);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return receptionWork;
    }
    public int getLastId() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT idReceptionWork FROM receptionwork";
        Connection connection = null;
        List<String> idStringList = new ArrayList<>();
        List<Integer> idIntList = new ArrayList<>();
        int maxId=1;
        int idInt;
        StringBuilder idString = new StringBuilder();
        String idResult ="";

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                idResult = resultSet.getString("idReceptionWork");
                idStringList.add(idResult);
            }

            if(!idStringList.isEmpty()){
                for(String idReceptionWork : idStringList){
                    idString = new StringBuilder();
                    for(int i = 4; i< idReceptionWork.length(); i++){
                        idString.append(String.valueOf(idReceptionWork.charAt(i)));
                    }
                    idInt = Integer.parseInt(idString.toString());
                    idIntList.add(idInt);
                }
                maxId = idIntList.get(0);
                for(int idReceptionWorkINT : idIntList){
                    if(idReceptionWorkINT > maxId){
                        maxId = idReceptionWorkINT;
                    }
                }

                maxId++;
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

        return maxId;
    }


}
