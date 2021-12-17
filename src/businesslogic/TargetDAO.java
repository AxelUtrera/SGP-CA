package businesslogic;

import dataaccess.Connector;
import domain.Target;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TargetDAO implements ITargetDAO{

    private final Connector CONNECTOR = new Connector();

    public boolean addTarget(Target target) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO target (`idTarget`, `description`, `title`, `status`, `keyWorkplan`) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idTarget = "TRG-"+ getLastIdTargetNumber();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idTarget);
            statement.setString(2, target.getDescription());
            statement.setString(3, target.getTitle());
            statement.setString(4, target.getStatus());
            statement.setString(5, target.getKeyWorkplan());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sqlException) {
            throw new BusinessLogicException("ConnectionException", sqlException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException sqlException) {
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }

        return operationResult;

    }

    public int getLastIdTargetNumber() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT convert(substring(idTarget, 5), UNSIGNED INTEGER) AS idTarget FROM target ORDER BY idTarget DESC limit 1";
        Connection connection = null;
        int lastIdTargetNumber=1;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                lastIdTargetNumber = resultSet.getInt("idTarget");
                lastIdTargetNumber++;
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

        return lastIdTargetNumber;
    }


    public boolean updateTarget(Target target) throws BusinessLogicException{

        final String SQL_UPDATE = "UPDATE target SET description=?, title=?, status=?, keyWorkplan=? WHERE idTarget=?";
        Connection connection = null;
        int numberOfUpdatedTargets;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, target.getDescription());
            statement.setString(2, target.getTitle());
            statement.setString(3, target.getStatus());
            statement.setString(4, target.getKeyWorkplan());
            statement.setString(5, target.getIdTarget());
            numberOfUpdatedTargets = statement.executeUpdate();

            if(numberOfUpdatedTargets>OPERATION_OK){
                operationResult = true;
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

        return operationResult;

    }

    public Target foundTargetByIdTarget(String idTarget) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM target WHERE idTarget = ?";
        Connection connection = null;
        Target target = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idTarget);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String description = resultSet.getString("description");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");
                String keyWorkplan = resultSet.getString("keyWorkplan");

                target = new Target(idTarget, description, title, status, keyWorkplan);
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

        return target;
    }

    public List<Target> foundTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM target WHERE keyWorkplan = ?";
        Connection connection = null;
        Target target;
        List<Target> targetList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, keyWorkplan);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                String description = resultSet.getString("description");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");

                target = new Target(idTarget, description, title, status, keyWorkplan);
                targetList.add(target);
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

        return targetList;
    }

    public List<Target> foundPendingTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM `sgp-ca`.target WHERE status = \"Pendiente\" AND keyWorkplan = ?";
        Connection connection = null;
        Target target;
        List<Target> targetList = new ArrayList<>();

        try {

            connection =  CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, keyWorkplan);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                String description = resultSet.getString("description");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");

                target = new Target(idTarget, description, title, status, keyWorkplan);
                targetList.add(target);
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

        return targetList;
    }

    public List<Target> foundAchievedTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM `sgp-ca`.target WHERE status = \"Cumplido\" AND keyWorkplan = ?";
        Connection connection = null;
        Target target;
        List<Target> targetList = new ArrayList<>();

        try {

            connection =  CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, keyWorkplan);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                String description = resultSet.getString("description");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");

                target = new Target(idTarget, description, title, status, keyWorkplan);
                targetList.add(target);
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

        return targetList;
    }



}
