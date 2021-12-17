package businesslogic;

import dataaccess.Connector;
import domain.Prototype;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrototypeDAO implements IPrototypeDAO{

    private final Connector CONNECTOR = new Connector();

    public List<Prototype> displayAllPrototypes() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM prototype";
        Connection connection = null;
        Prototype prototype;
        List<Prototype> prototypeList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idPrototype = resultSet.getString("idprototype");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                prototype = new Prototype (type, title,academicDegree, idInvestigationProject, idPrototype);
                prototypeList.add(prototype);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrototypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return prototypeList;
    }

    public boolean addPrototype(Prototype prototype) throws BusinessLogicException{


        final String SQL_INSERT = "INSERT INTO prototype (`idPrototype`, `type`, `title`, `academicDegree`, `idInvestigationProject`) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;
        String id = "PRT-"+getLastId();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, id);
            statement.setString(2, prototype.getType());
            statement.setString( 3, prototype.getTitle());
            statement.setString(4, prototype.getAcademicdegree());
            statement.setString(5, prototype.getIdInvestigationProject());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrototypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }


    public boolean updatePrototype(Prototype prototype) throws BusinessLogicException{


        String SQL_UPDATE = "UPDATE prototype SET idPrototype=?, `type`=?, title=?, academicDegree=?, idInvestigationProject=? WHERE idPrototype=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, prototype.getIdPrototype());
            statement.setString(2, prototype.getType());
            statement.setString( 3, prototype.getTitle());
            statement.setString(4, prototype.getAcademicdegree());
            statement.setString(5, prototype.getIdInvestigationProject());
            statement.setString(6, prototype.getIdPrototype());
            statement.executeUpdate();
            operationResult = true;


        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrototypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deletePrototype(String idPrototype) throws BusinessLogicException{


        String SQL_DELETE = "DELETE FROM prototype WHERE idPrototype=?";
        Connection connection = null;
        boolean operationResult = false;


        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idPrototype);
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrototypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public Prototype foundPrototypeById(String idprototype) throws BusinessLogicException{


        String SQL_SELECT = "SELECT * FROM prototype WHERE idPrototype = ?";
        Connection connection = null;
        Prototype prototype = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idprototype);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idPrototype = resultSet.getString("idprototype");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                prototype = new Prototype(type, title,academicDegree, idInvestigationProject, idPrototype);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PrototypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return prototype;
    }

    public int getLastId() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT idPrototype FROM prototype";
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
                idResult = resultSet.getString("idPrototype");
                idStringList.add(idResult);
            }

            if(!idStringList.isEmpty()){
                for(String idPrototype : idStringList){
                    idString = new StringBuilder();
                    for(int i = 4; i< idPrototype.length(); i++){
                        idString.append(String.valueOf(idPrototype.charAt(i)));
                    }
                    idInt = Integer.parseInt(idString.toString());
                    idIntList.add(idInt);
                }
                maxId = idIntList.get(0);
                for(int idPrototypeINT : idIntList){
                    if(idPrototypeINT > maxId){
                        maxId = idPrototypeINT;
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




