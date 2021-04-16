package businesslogic;

import dataaccess.Connector;
import domain.Strategy;
import domain.Target;
import domain.TargetStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TargetDAO implements ITargetDAO{

    private Connector connector = new Connector();

    public List<Target> displayAllTargets(){

        String SQL_SELECT = "SELECT * FROM target";
        Connection connection = null;
        Target target;
        List<Target> targetList = new ArrayList<>();

        try {

            connection =  connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                String descripcion = resultSet.getString("descripcion");
                String title = resultSet.getString("title");

                 target = new Target(idTarget, descripcion, title);
                targetList.add(target);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return targetList;
    }

    public int addTarget(Target target){

        String SQL_INSERT = "INSERT INTO target (`idTarget`, `descripcion`, `title`) VALUES (?, ?, ?)";
        Connection connection = null;
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, target.getIdTarget());
            statement.setString(2, target.getDescripcion());
            statement.setString(3, target.getTitle());
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;

    }


    public int updateOneTarget(Target target){

        String SQL_UPDATE = "UPDATE target SET idTarget=?, descripcion=?, title=? WHERE idTarget=?";
        Connection connection = null;
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, target.getIdTarget());
            statement.setString(2, target.getDescripcion());
            statement.setString(3, target.getTitle());
            statement.setString(4, target.getIdTarget());
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;

    }

    public int deleteOneTarget(String idTarget){

        String SQL_DELETE = "DELETE FROM target WHERE idTarget=?";
        Connection connection = null;
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idTarget);
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public List<Strategy> displayAllTargetStrategies(String idTarget){

        String SQL_SELECT = "SELECT * FROM `target-strategy` WHERE idTarget = ?";
        Connection connection = null;
        List<Strategy> targetStrategyList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idTarget);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategy = resultSet.getString("idStrategy");
                StrategyDAO strategyDAO = new StrategyDAO();
                targetStrategyList.add(strategyDAO.foundStrategyById(idStrategy));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return targetStrategyList;
    }

    public void insertOneTargetStrategy(TargetStrategy targetStrategy){


        String SQL_INSERT = "INSERT INTO `target-strategy` (`idTargetStrategy`,`idTarget`, `idStrategy`) VALUES (?, ?, ?)";
        Connection conn = null;

        try {

            conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, targetStrategy.getIdTarget());
            stmt.setString(2, targetStrategy.getIdStrategy());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }







    public Target foundTargetById(String idTarget){

        String SQL_FOUNDTARGETBYID = "SELECT * FROM target WHERE idTarget = ?";
        Connection connection = null;
        Target target = null;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FOUNDTARGETBYID);
            statement.setString(1, idTarget);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idStrategyResult = resultSet.getString("idTarget");
                String descripcion = resultSet.getString("descripcion");
                String title = resultSet.getString("title");

                target = new Target(idStrategyResult, descripcion, title);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TargetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return target;
    }



}
