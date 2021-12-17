package businesslogic;

import dataaccess.Connector;
import domain.AcademicGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcademicGroupDAO implements IAcademicGroupDAO{

    private final Connector CONNECTOR = new Connector();

    public List<AcademicGroup> displayAllAcademicGroups() throws BusinessLogicException {

        String SQL_SELECT = "SELECT * FROM academicgroup";
        Connection connection = null;
        AcademicGroup academicGroup;
        List<AcademicGroup> academicGroupList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String key = resultSet.getString("key");
                String name = resultSet.getString("name");
                String reponsible = resultSet.getString("reponsible");
                String degreeConsolidation = resultSet.getString("degreeConsolidation");

                academicGroup = new AcademicGroup(key, name, reponsible, degreeConsolidation);
                academicGroupList.add(academicGroup);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicGroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return academicGroupList;
    }

    public boolean addAcademicGroup(AcademicGroup academicGroup)throws BusinessLogicException {

        String SQL_INSERT = "INSERT INTO academicgroup (`key`, `name`, `reponsible`, `degreeConsolidation`) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;
        PreparedStatement statement = null;


        try {

            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, academicGroup.getKey());
            statement.setString(2, academicGroup.getName());
            statement.setString(3, academicGroup.getResponsible());
            statement.setString(4, academicGroup.getDegreeConsolidation());

            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicGroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return operationResult;
    }


    public boolean updateAcademicGroup(AcademicGroup academicGroup) throws BusinessLogicException{

        String SQL_UPDATE = "UPDATE academicgroup SET name=?, reponsible=?, degreeConsolidation=?  WHERE `key`=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, academicGroup.getName());
            statement.setString(2, academicGroup.getResponsible());
            statement.setString(3, academicGroup.getDegreeConsolidation());
            statement.setString(4, academicGroup.getKey());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicGroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteAcademicGroup(String key) throws BusinessLogicException {

        String SQL_DELETE = "DELETE FROM academicgroup WHERE `key`=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, key);
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public AcademicGroup foundAcademicGroupByID(String id) throws BusinessLogicException{

        String SQL_SELECT = "SELECT * FROM academicgroup WHERE `key`=?";
        Connection connection = null;
        AcademicGroup academicGroup = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String key = resultSet.getString("key");
                String name = resultSet.getString("name");
                String responsible = resultSet.getString("responsible");
                String degreeConsolidation = resultSet.getString("degreeConsolidation");


                academicGroup = new AcademicGroup (key, name, responsible, degreeConsolidation);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicGroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return academicGroup;
    }

    public AcademicGroup foundAcademicGroupByName(String nameAG) throws BusinessLogicException{

        String SQL_SELECT = "SELECT * FROM academicgroup WHERE title = ?";
        Connection connection = null;
        AcademicGroup academicGroup = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, nameAG);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String key = resultSet.getString("key");
                String name = resultSet.getString("name");
                String reponsible = resultSet.getString("reponsible");
                String degreeConsolidation = resultSet.getString("degreeConsolidation");


                academicGroup = new AcademicGroup(key, name, reponsible, degreeConsolidation);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicGroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return academicGroup;
    }

}
