package businesslogic;

import dataaccess.Connector;
import domain.Strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StrategyDAO implements IStrategyDAO{

    private Connector connector = new Connector();

    public List<Strategy> displayAllStrategies(){

        String SQL_SELECT = "SELECT * FROM strategy";
        Connection connection = null;
        Strategy strategy;
        List<Strategy> strategyList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategy = resultSet.getString("idStrategy");
                String action = resultSet.getString("action");
                String description = resultSet.getString("description");
                String goal = resultSet.getString("goal");
                int number = resultSet.getInt("number");
                String result = resultSet.getString("result");

                strategy = new Strategy(idStrategy, action, description, goal, number, result);
                strategyList.add(strategy);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return strategyList;
    }

    public int addStrategy(Strategy strategy){

        String SQL_INSERT = "INSERT INTO strategy (`idStrategy`, `action`, `description`, `goal`, `number`, `result`) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        int numberOfRegisters =0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, strategy.getIdStrategy());
            statement.setString(2, strategy.getAction());
            statement.setString(3, strategy.getDescription());
            statement.setString(4, strategy.getGoal());
            statement.setInt(5, strategy.getStrategyNumber());
            statement.setString(6, strategy.getResult());
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public int deleteOneStrategy(String idStrategy){

        String SQL_DELETE = "DELETE FROM strategy WHERE idStrategy=?";
        Connection connection = null;
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idStrategy);
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public Strategy foundStrategyById(String idStrategy){

        String SQL_FOUNDSTRATEGYBYID = "SELECT * FROM strategy WHERE idStrategy = ?";
        Connection connection = null;
        Strategy strategy = null;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FOUNDSTRATEGYBYID);
            statement.setString(1, idStrategy);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategyResult = resultSet.getString("idStrategy");
                String action = resultSet.getString("action");
                String description = resultSet.getString("description");
                String goal = resultSet.getString("goal");
                int number = resultSet.getInt("number");
                String result = resultSet.getString("result");

                strategy = new Strategy(idStrategyResult, action, description, goal, number, result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(StrategyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return strategy;
    }

}
