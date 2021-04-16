package businesslogic;

import dataaccess.Connector;
import domain.Target;
import domain.WorkPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkPlanDAO implements IWorkPlanDAO{

    private Connector connector = new Connector();

    public List<WorkPlan> displayAllWorkPlans(){

        String SQL_SELECT = "SELECT * FROM workplan";
        Connection connection = null;
        WorkPlan workPlan;
        List<WorkPlan> workPlanList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String key = resultSet.getString("key");
                Date starDate = resultSet.getDate("starDate");
                Date endDate = resultSet.getDate("endDate");

                workPlan = new WorkPlan(key, starDate, endDate);
                workPlanList.add(workPlan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return workPlanList;
    }

    public int addWorkPlan(WorkPlan workPlan){

        String SQL_INSERT = "INSERT INTO workplan (`key`, `starDate`, `endDate`) VALUES (?, ?, ?)";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getStarDate()));
        String finishDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getEndDate()));
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, workPlan.getKey());
            statement.setString(2, startDate);
            statement.setString(3, finishDate);
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public int updateOneWorkPlan(WorkPlan workPlan){

        String SQL_UPDATE = "UPDATE workplan SET key=?, starDate=?, endDate=? WHERE key=?";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getStarDate()));
        String finishDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getEndDate()));
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, workPlan.getKey());
            statement.setString(2, startDate);
            statement.setString(3, finishDate);
            statement.setString(4, workPlan.getKey());
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public int deleteOneWorkPlan(String workPlanKey){

        String SQL_DELETE = "DELETE FROM workplan WHERE key=?";
        Connection connection = null;
        int numberOfRegisters=0;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, workPlanKey);
            numberOfRegisters = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfRegisters;
    }

    public List<Target> displayAllWorkPlanTargets(String workPlanKey){

        String SQL_SELECT = "SELECT * FROM `workplan-target` WHERE keyWorkPlan = ?";
        Connection connection = null;
        List<Target> workPlanTargetList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, workPlanKey);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTarget = resultSet.getString("idTarget");
                TargetDAO targetDAO = new TargetDAO();
                workPlanTargetList.add(targetDAO.foundTargetById(idTarget));
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return workPlanTargetList;
    }

    public WorkPlan foundWorkPlanByKey(String workPlanKey){

        String SQL_FOUNDWORKPLANBYID = "SELECT * FROM workplan WHERE workplan.key = ?";
        Connection connection = null;
        WorkPlan workPlan = null;

        try {

            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FOUNDWORKPLANBYID);
            statement.setString(1, workPlanKey);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String key = resultSet.getString("key");
                Date starDate = resultSet.getDate("starDate");
                Date endDate = resultSet.getDate("endDate");

                workPlan = new WorkPlan(key, starDate, endDate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(WorkPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return workPlan;
    }

}
