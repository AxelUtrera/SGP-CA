package businesslogic;

import dataaccess.Connector;
import domain.Strategy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StrategyDAO implements IStrategyDAO{

    private final Connector CONNECTOR = new Connector();

    public List<Strategy> displayAllStrategies() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM strategy";
        Connection connection = null;
        Strategy strategy;
        List<Strategy> strategyList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategy = resultSet.getString("idStrategy");
                String action = resultSet.getString("action");
                String description = resultSet.getString("description");
                String goal = resultSet.getString("goal");
                int number = resultSet.getInt("number");
                String result = resultSet.getString("result");
                String idTarget = resultSet.getString("idTarget");

                strategy = new Strategy(idStrategy, action, description, goal, number, result, idTarget);
                strategyList.add(strategy);
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

        return strategyList;
    }

    public boolean addStrategy(Strategy strategy) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO strategy (`idStrategy`, `action`, `description`, `goal`," +
                " `number`, `result`, `idTarget`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idStrategy = "STR-"+ getLastIdStrategyNumber();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idStrategy);
            statement.setString(2, strategy.getAction());
            statement.setString(3, strategy.getDescription());
            statement.setString(4, strategy.getGoal());
            statement.setInt(5, strategy.getNumber());
            statement.setString(6, strategy.getResult());
            statement.setString(7, strategy.getIdTarget());
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

    public int getLastIdStrategyNumber() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT convert(substring(idStrategy, 5), UNSIGNED INTEGER)" +
                " AS idStrategy FROM strategy ORDER BY idStrategy DESC limit 1";
        Connection connection = null;
        int lastIdStrategyNumber=1;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                lastIdStrategyNumber = resultSet.getInt("idStrategy");
                lastIdStrategyNumber++;
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

        return lastIdStrategyNumber;
    }

    public boolean deleteStrategy(String idStrategy) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM strategy WHERE idStrategy=?";
        Connection connection = null;
        int numberOfStrategiesDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idStrategy);
            numberOfStrategiesDeleted = statement.executeUpdate();

            if(numberOfStrategiesDeleted>OPERATION_OK){
                operationResult = true;
            }

        } catch (SQLException sqlException) {
            throw new BusinessLogicException("ConnectionException", sqlException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException sqlException) {
                throw new BusinessLogicException("ConnectionException", sqlException);
            }
        }

        return operationResult;
    }

    public Strategy foundStrategyByIdStrategy(String idStrategy) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM strategy WHERE idStrategy = ?";
        Connection connection = null;
        Strategy strategy = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idStrategy);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String action = resultSet.getString("action");
                String description = resultSet.getString("description");
                String goal = resultSet.getString("goal");
                int number = resultSet.getInt("number");
                String result = resultSet.getString("result");
                String idTarget = resultSet.getString("idTarget");

                strategy = new Strategy(idStrategy, action, description, goal, number, result, idTarget);
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

        return strategy;
    }

    public List<Strategy> foundStrategiesByIdTarget(String idTarget) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM strategy WHERE idTarget = ?";
        Connection connection = null;
        Strategy strategy;
        List<Strategy> strategyList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idTarget);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategy = resultSet.getString("idStrategy");
                String action = resultSet.getString("action");
                String description = resultSet.getString("description");
                String goal = resultSet.getString("goal");
                int number = resultSet.getInt("number");
                String result = resultSet.getString("result");

                strategy = new Strategy(idStrategy, action, description, goal, number, result, idTarget);
                strategyList.add(strategy);
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

        return strategyList;
    }


}
