package businesslogic;

import dataaccess.Connector;
import domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberDAO implements IMemberDAO{

    private final Connector CONNECTOR = new Connector();

    public List<Member> displayAllMembers() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM member";
        Connection connection = null;
        Member member;
        List<Member> memberList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String curp = resultSet.getString("curp");
                String name = resultSet.getString("name");
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                String institutionalMail = resultSet.getString("institutionalMail");
                String telephoneNumber = resultSet.getString("telephoneNumber");
                String academicRole = resultSet.getString("academicRole");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");
                String password = resultSet.getString("password");

                member = new Member (curp, name, dateOfBirth, institutionalMail,telephoneNumber,academicRole,keyAcademicGroup, password);
                memberList.add(member);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return memberList;
    }

    public boolean addMember(Member member) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO member (`curp`, `name`, `dateofBirth`, `institutionalMail`," +
                " `telephoneNumber`, `academicRole`, `keyAcademicGroup`,`password` ) VALUES (?, ?, ?, ?, ?, ?, ?, AES_ENCRYPT(?, 'AES'))";
        Connection connection = null;
        boolean operationResult = false;

        try {
            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, member.getCurp());
            statement.setString(2, member.getName());
            statement.setDate(3, member.getDateofBirth());
            statement.setString(4, member.getInstitutionalMail());
            statement.setString(5, member.getTelephoneNumber());
            statement.setString(6, member.getAcademicRole());
            statement.setString(7, member.getKeyAcademicGroup());
            statement.setString(8, member.getPassword());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public boolean updateMember(Member member) throws BusinessLogicException{


        final String SQL_UPDATE = "UPDATE member SET curp=?, name=?, dateofBirth=?, institutionalMail=?, telephoneNumber=?," +
                " academicRole=?, keyAcademicGroup=?, password=? WHERE curp=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, member.getCurp());
            statement.setString(2, member.getName());
            statement.setDate( 3, member.getDateofBirth());
            statement.setString(4, member.getInstitutionalMail());
            statement.setString(5, member.getTelephoneNumber());
            statement.setString(6, member.getAcademicRole());
            statement.setString(7, member.getKeyAcademicGroup());
            statement.setString(8, member.getPassword());
            statement.setString(9, member.getCurp());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteMember(String curp) throws BusinessLogicException{


        final String SQL_DELETE = "DELETE FROM member WHERE curp=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, curp);
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

    public Member findMemberByCurp(String curp) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM member WHERE curp = ?";
        Connection connection = null;
        Member member = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, curp);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString("name");
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                String institutionalMail = resultSet.getString("institutionalMail");
                String telephoneNumber = resultSet.getString("telephoneNumber");
                String academicRole = resultSet.getString("academicRole");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");
                String password = resultSet.getString("password");

                member = new Member(curp, name, dateOfBirth, institutionalMail,telephoneNumber,academicRole,keyAcademicGroup, password);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return member;
    }

    public Member findMemberByName(String nameFind) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM member WHERE name = ?";
        Connection connection = null;
        Member member = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, nameFind);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String curp = resultSet.getString("curp");
                String name = resultSet.getString("name");
                java.sql.Date dateOfBirth = resultSet.getDate("dateOfBirth");
                String institutionalMail = resultSet.getString("institutionalMail");
                String telephoneNumber = resultSet.getString("telephoneNumber");
                String academicRole = resultSet.getString("academicRole");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");
                String password = resultSet.getString("password");

                member = new Member (curp, name, dateOfBirth, institutionalMail,telephoneNumber,academicRole,keyAcademicGroup, password);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(businesslogic.MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return member;
    }

    public ObservableList<String> parseToObservableList() {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        try{
            List<Member> list = displayAllMembers();
            for (Member l :list){
                observableList.add(l.getName());
            }
        } catch (BusinessLogicException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return observableList;
    }

    public Member findMemberByInstitutionalMail(String institutionalMail) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM member WHERE institutionalMail = ?";
        Connection connection = null;
        Member member = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, institutionalMail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String CURP = resultSet.getString("CURP");
                String name = resultSet.getString("name");
                java.sql.Date dateOfBirth = resultSet.getDate("dateOfBirth");
                String telephoneNumber = resultSet.getString("telephoneNumber");
                String academicRole = resultSet.getString("academicRole");
                String password = resultSet.getString("password");
                String keyAcademicGroup = resultSet.getString("keyAcademicGroup");

                member = new Member (CURP, name, dateOfBirth, institutionalMail,telephoneNumber,academicRole, keyAcademicGroup, password);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(businesslogic.MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return member;
    }

    public String decryptPassword(String institutionalMail) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT AES_DECRYPT(UNHEX(password), 'AES') RECUPERADO FROM member WHERE institutionalMail = ?";
        Connection connection = null;
        String password = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, institutionalMail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("RECUPERADO");
            }

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(businesslogic.MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return password;
    }

}


