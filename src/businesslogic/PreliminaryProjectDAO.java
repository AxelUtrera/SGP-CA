package businesslogic;

import dataaccess.Connector;
import domain.PreliminaryProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PreliminaryProjectDAO implements IPreliminaryProjectDAO {

    private final Connector CONNECTOR = new Connector();

    public List<PreliminaryProject> displayAllPreliminaryProjects() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM preliminaryproject";
        Connection connection = null;
        PreliminaryProject preliminaryProject;
        List<PreliminaryProject> preliminaryProjectList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String idPreliminaryProject = resultSet.getString("idPreliminaryProject");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date startDate = resultSet.getDate("startDate");
                String status = resultSet.getString("status");
                String modality = resultSet.getString("modality");
                int duration = resultSet.getInt("duration");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                preliminaryProject = new PreliminaryProject(idPreliminaryProject, title, description, startDate,
                        status, modality, duration, idInvestigationProject);
                preliminaryProjectList.add(preliminaryProject);
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

        return preliminaryProjectList;
    }

    public boolean addPreliminaryProject(PreliminaryProject preliminaryProject) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO preliminaryproject (`idPreliminaryProject`, `title`, `description`, `startDate`, `status`," +
                " `modality`, `duration`, `idInvestigationProject`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(preliminaryProject.getStartDate()));
        boolean operationResult = false;

        String idPreliminaryProject = "PLP-"+ getLastIdNumber("idPreliminaryProject", "preliminaryproject");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idPreliminaryProject);
            statement.setString(2, preliminaryProject.getTitle());
            statement.setString(3, preliminaryProject.getDescription());
            statement.setString(4, startDate);
            statement.setString(5, preliminaryProject.getStatus());
            statement.setString(6, preliminaryProject.getModality());
            statement.setInt(7, preliminaryProject.getDuration());
            statement.setString(8, preliminaryProject.getIdInvestigationProject());
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

    public int getLastIdNumber(String column, String table) throws BusinessLogicException{

        final String SQL_SELECT = String.format("SELECT convert(substring(%s, 5), UNSIGNED INTEGER) AS %s FROM %s ORDER BY %s DESC limit 1",
                column, column, table, column);
        Connection connection = null;
        int lastIdNumber=1;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                lastIdNumber = resultSet.getInt(column);
                lastIdNumber++;
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

        return lastIdNumber;
    }

    public boolean updatePreliminaryProject(PreliminaryProject preliminaryProject) throws BusinessLogicException{

        final String SQL_UPDATE = "UPDATE preliminaryproject SET title=?, description=?, startDate=?, status=?," +
                " modality=?, duration=?, idInvestigationProject=? WHERE idPreliminaryProject=?";
        Connection connection = null;
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(preliminaryProject.getStartDate()));
        int numberOfUpdatedPreliminaryProjects;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, preliminaryProject.getTitle());
            statement.setString(2, preliminaryProject.getDescription());
            statement.setString(3, startDate);
            statement.setString(4, preliminaryProject.getStatus());
            statement.setString(5, preliminaryProject.getModality());
            statement.setInt(6, preliminaryProject.getDuration());
            statement.setString(7, preliminaryProject.getIdInvestigationProject());
            statement.setString(8, preliminaryProject.getIdPreliminaryProyect());
            numberOfUpdatedPreliminaryProjects = statement.executeUpdate();

            if(numberOfUpdatedPreliminaryProjects>OPERATION_OK){
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

    public boolean deletePreliminaryProject(String idPreliminaryProject) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM preliminaryproject WHERE idPreliminaryProject=?";
        Connection connection = null;
        int numberOfPreliminaryProjectsDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idPreliminaryProject);
            numberOfPreliminaryProjectsDeleted = statement.executeUpdate();

            if(numberOfPreliminaryProjectsDeleted>OPERATION_OK){
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

    public PreliminaryProject foundPreliminaryProjectById(String idPreliminaryProject) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM preliminaryproject WHERE idPreliminaryProject = ?";
        Connection connection = null;
        PreliminaryProject preliminaryProject = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idPreliminaryProject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date startDate = resultSet.getDate("startDate");
                String status = resultSet.getString("status");
                String modality = resultSet.getString("modality");
                int duration = resultSet.getInt("duration");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                preliminaryProject = new PreliminaryProject(idPreliminaryProject, title, description, startDate,
                        status, modality, duration, idInvestigationProject);
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

        return preliminaryProject;
    }

    public List<PreliminaryProject> foundPreliminaryProjectsByIdInvestigationProject(String idInvestigationProject) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM preliminaryproject WHERE idInvestigationProject = ?";
        Connection connection = null;
        PreliminaryProject preliminaryProject;
        List<PreliminaryProject> preliminaryProjectList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idInvestigationProject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String idPreliminaryProject = resultSet.getString("idPreliminaryProject");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date startDate = resultSet.getDate("startDate");
                String status = resultSet.getString("status");
                String modality = resultSet.getString("modality");
                int duration = resultSet.getInt("duration");

                preliminaryProject = new PreliminaryProject(idPreliminaryProject, title, description, startDate, status,
                        modality, duration, idInvestigationProject);
                preliminaryProjectList.add(preliminaryProject);
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

        return preliminaryProjectList;
    }

    public List<String> foundStudentInformationByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT studentName, studentEmail FROM `preliminaryproject-studentinformation` WHERE idPreliminaryProject=?";
        Connection connection = null;
        List<String> preliminaryProjectStudentNamesList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idPreliminaryProject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String studentName = resultSet.getString("studentName");
                String studentEmail = resultSet.getString("studentEmail");
                String studentInformation = studentName + " [" + studentEmail + "]";

                preliminaryProjectStudentNamesList.add(studentInformation);
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

        return preliminaryProjectStudentNamesList;
    }

    public List<String> foundLgacByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT lgac FROM `lgac-preliminaryproject` WHERE idPreliminaryProject=?";
        Connection connection = null;
        List<String> preliminaryProjectLgacList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idPreliminaryProject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String lgac = resultSet.getString("lgac");

                preliminaryProjectLgacList.add(lgac);
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

        return preliminaryProjectLgacList;
    }

    public List<String> foundCodirectorsByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT codirectorName, codirectorEmail FROM `preliminaryproject-codirector` WHERE idPreliminaryProject=?";
        Connection connection = null;
        List<String> preliminaryProjectCodirectosList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idPreliminaryProject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String codirectorName = resultSet.getString("codirectorName");
                String codirectorEmail = resultSet.getString("codirectorEmail");
                String codirectorInformation = codirectorName + " [" + codirectorEmail + "]";

                preliminaryProjectCodirectosList.add(codirectorInformation);
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

        return preliminaryProjectCodirectosList;
    }

    public boolean addStudentInformationToPreliminaryProject(String idPreliminaryProject, String studentInformation) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO `preliminaryproject-studentinformation` (`idPreliminaryProjectStudentInformation`," +
                " `idPreliminaryProject`, `studentName`, `studentEmail`) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idPreliminaryProjectStudentInformation = "PSN-"+ getLastIdNumber("idPreliminaryProjectStudentInformation",
                "`preliminaryproject-studentinformation`");

        String[] studentName = studentInformation.split("\\[");
        String[] studentEmail = studentName[1].split("\\]");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idPreliminaryProjectStudentInformation);
            statement.setString(2, idPreliminaryProject);
            statement.setString(3, studentName[0].trim());
            statement.setString(4, studentEmail[0].trim());
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

    public boolean addCodirectorToPreliminaryProject(String idPreliminaryProject, String codirectorInformation) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO `sgp-ca`.`preliminaryproject-codirector` (`idPreliminaryProjectCodirector`, `codirectorName`," +
                " `codirectorEmail`, `idPreliminaryProject`) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idPreliminaryProjectCodirector = "PPC-"+ getLastIdNumber("idPreliminaryProjectCodirector",
                "`preliminaryproject-codirector`");

        String[] codirectorName = codirectorInformation.split("\\[");
        String[] codirectorEmail = codirectorName[1].split("\\]");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idPreliminaryProjectCodirector);
            statement.setString(2, codirectorName[0].trim());
            statement.setString(3, codirectorEmail[0].trim());
            statement.setString(4, idPreliminaryProject);
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

    public boolean addLgacToPreliminaryProject(String idPreliminaryProject, String lgac) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO `lgac-preliminaryproject` (`idLgacPreliminaryProject`," +
                " `lgac`, `idPreliminaryProject`) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idLgacPreliminaryProject = "LPP-"+ getLastIdNumber("idLgacPreliminaryProject", "`lgac-preliminaryproject`");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idLgacPreliminaryProject);
            statement.setString(2, lgac);
            statement.setString(3, idPreliminaryProject);
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

    public boolean deletePreliminaryProjectStudent(String idPreliminaryProject, String studentInformation) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM `preliminaryproject-studentinformation` WHERE idPreliminaryProject=? AND studentEmail=?";
        Connection connection = null;
        int numberOfPreliminaryProjectStudentNamesDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        String[] studentName = studentInformation.split("\\[");
        String[] studentEmail = studentName[1].split("\\]");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idPreliminaryProject);
            statement.setString(2, studentEmail[0]);
            numberOfPreliminaryProjectStudentNamesDeleted = statement.executeUpdate();

            if(numberOfPreliminaryProjectStudentNamesDeleted>OPERATION_OK){
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

    public boolean deletePreliminaryProjectLgac(String idPreliminaryProject, String lgac) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM `lgac-preliminaryproject` WHERE idPreliminaryProject=? AND lgac=?";
        Connection connection = null;
        int numberOfPreliminaryProjectLgacDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idPreliminaryProject);
            statement.setString(2, lgac);
            numberOfPreliminaryProjectLgacDeleted = statement.executeUpdate();

            if(numberOfPreliminaryProjectLgacDeleted > OPERATION_OK){
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

    public boolean deletePreliminaryProjectCodirector(String idPreliminaryProject, String codirectorInformation) throws BusinessLogicException{

        final String SQL_DELETE = "DELETE FROM `preliminaryproject-codirector` WHERE idPreliminaryProject=? AND codirectorEmail=?";
        Connection connection = null;
        int numberOfPreliminaryProjectStudentNamesDeleted;
        boolean operationResult = false;
        final int OPERATION_OK = 0;

        String[] codirectorName = codirectorInformation.split("\\[");
        String[] codirectorEmail = codirectorName[1].split("\\]");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, idPreliminaryProject);
            statement.setString(2, codirectorEmail[0]);
            numberOfPreliminaryProjectStudentNamesDeleted = statement.executeUpdate();

            if(numberOfPreliminaryProjectStudentNamesDeleted>OPERATION_OK){
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



}
