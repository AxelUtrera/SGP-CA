package businesslogic;

import dataaccess.Connector;
import domain.TargetStrategy;
import domain.WorkPlanTarget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkPlanTargetDAO implements IWorkPlanTargetDAO{

    private Connector connector = new Connector();
    private static final String SQL_SELECT = "SELECT * FROM `workplan-target`";
    private static final String SQL_INSERT = "INSERT INTO `workplan-target` (`idTarget`, `keyWorkPlan`) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE `workplan-target` SET idTarget=?, keyWorkPlan=? WHERE idTarget=?";
    private static final String SQL_DELETE = "DELETE FROM `workplan-target` WHERE idTarget=?";

    public List<WorkPlanTarget> displayAllWorkPlanTargets(){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        WorkPlanTarget workPlanTarget = null;
        List<WorkPlanTarget> workPlanTargetList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                String keyWorkPlan = resultSet.getString("keyWorkPlan");

                workPlanTarget = new WorkPlanTarget(idTarget, keyWorkPlan);
                workPlanTargetList.add(workPlanTarget);
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

        return workPlanTargetList;
    }

    public void insertOneWorkPlanTarget(WorkPlanTarget workPlanTarget){

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, workPlanTarget.getIdTarget());
            stmt.setString(2, workPlanTarget.getKeyWorkPlan());
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

    public void updateOneWorkPlanTarget(WorkPlanTarget workPlanTarget){

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, workPlanTarget.getIdTarget());
            stmt.setString(2, workPlanTarget.getKeyWorkPlan());
            stmt.setString(3, workPlanTarget.getIdTarget());
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

    public void deleteOneWorkPlanTarget(WorkPlanTarget workPlanTarget){

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connector.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, workPlanTarget.getIdTarget());
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
