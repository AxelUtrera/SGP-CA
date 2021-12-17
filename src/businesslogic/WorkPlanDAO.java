package businesslogic;

import dataaccess.Connector;
import domain.WorkPlan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkPlanDAO implements IWorkPlanDAO {

    private final Connector CONNECTOR = new Connector();

    public List<WorkPlan> displayAllWorkPlans() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM workplan";
        Connection connection = null;
        WorkPlan workPlan;
        List<WorkPlan> workPlanList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String keyWorkPlan = resultSet.getString("keyWorkPlan");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");
                String curpMember = resultSet.getString("curpMember");

                workPlan = new WorkPlan(keyWorkPlan, startDate, endDate, keyAcademicGroup, curpMember);
                workPlanList.add(workPlan);
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

        return workPlanList;
    }

    public boolean addWorkPlan(WorkPlan workPlan) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO workplan (`keyWorkPlan`, `startDate`, `endDate`, `keyAcademicGroup`, `curpMember`)" +
                " VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getStartDate()));
        String finishDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getEndDate()));
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, workPlan.getKeyWorkPlan());
            statement.setString(2, startDate);
            statement.setString(3, finishDate);
            statement.setString(4, workPlan.getKeyAcademicGroup());
            statement.setString(5, workPlan.getCurpMember());
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

    public boolean updateWorkPlan(WorkPlan workPlan, String originalKeyWorkPlan) throws BusinessLogicException{

        final String SQL_UPDATE = "UPDATE workplan SET keyWorkPlan=?, startDate=?, endDate=?, keyAcademicGroup=?, curpMember=? WHERE keyWorkPlan=?";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getStartDate()));
        String finishDate = (new SimpleDateFormat("yyyy-MM-dd").format(workPlan.getEndDate()));
        int numberOfUpdatedWorkPlans;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, workPlan.getKeyWorkPlan());
            statement.setString(2, startDate);
            statement.setString(3, finishDate);
            statement.setString(4, workPlan.getKeyAcademicGroup());
            statement.setString(5, workPlan.getCurpMember());
            statement.setString(6, originalKeyWorkPlan);
            numberOfUpdatedWorkPlans = statement.executeUpdate();

            if(numberOfUpdatedWorkPlans > OPERATION_OK){
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

    public boolean deleteWorkPlan(String keyWorkPlan) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM workplan WHERE keyWorkPlan=?";
        Connection connection = null;
        int numberOfWorkPlansDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, keyWorkPlan);
            numberOfWorkPlansDeleted = statement.executeUpdate();

            if(numberOfWorkPlansDeleted>OPERATION_OK){
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

    public WorkPlan foundWorkPlanByKeyWorkPlan(String keyWorkPlan) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM workplan WHERE keyWorkPlan = ?";
        Connection connection = null;
        WorkPlan workPlan = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, keyWorkPlan);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");
                String curpMember = resultSet.getString("curpMember");

                workPlan = new WorkPlan(keyWorkPlan, startDate, endDate, keyAcademicGroup, curpMember);
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

        return workPlan;
    }


}
