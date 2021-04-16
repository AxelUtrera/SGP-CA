package businesslogic;

import dataaccess.Connector;
import domain.TargetStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TargetStrategyDAO implements ITargetStrategyDAO{

    private static final String SQL_SELECT = "SELECT * FROM `target-strategy`";
    private static final String SQL_UPDATE = "UPDATE `target-strategy` SET idTargetStrategy=?, idTarget=?, idStrategy=? WHERE idTargetStrategy=?";
    private static final String SQL_DELETE = "DELETE FROM `target-strategy` WHERE idTargetStrategy=?";
    private Connector connector = new Connector();

    public List<TargetStrategy> displayAllTargetStrategies(){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TargetStrategy targetStrategy = null;
        List<TargetStrategy> targetStrategyList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTargetStrategy = resultSet.getString("idTarget-strategy");
                String idTarget = resultSet.getString("idTarget");
                String idStrategy = resultSet.getString("idStrategy");

                targetStrategy = new TargetStrategy(idTargetStrategy, idTarget, idStrategy);
                targetStrategyList.add(targetStrategy);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return targetStrategyList;
    }

    public void insertOneTargetStrategy(TargetStrategy targetStrategy){

        String SQL_INSERT = "INSERT INTO `target-strategy` (`idTargetStrategy`,`idTarget`, `idStrategy`) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
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

    public void updateOneTargetStrategy(TargetStrategy targetStrategy){

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, targetStrategy.getIdTarget());
            stmt.setString(2, targetStrategy.getIdStrategy());
            stmt.setString(3, targetStrategy.getIdStrategy());
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

    public void deleteOneTargetStrategy(TargetStrategy targetStrategy){

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, targetStrategy.getIdStrategy());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }


}
