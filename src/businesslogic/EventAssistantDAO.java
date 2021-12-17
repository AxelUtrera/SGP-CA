package businesslogic;

import com.itextpdf.text.pdf.BaseFont;
import dataaccess.Connector;
import domain.EventAssistant;
import domain.EventConstancy;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.Chunk;
import domain.Member;

public class EventAssistantDAO implements IEventAssistantDAO{

    private final Connector CONNECTOR = new Connector();

    public List<EventConstancy> displayAllEventConstanciesByIdAcademicEvent(String idAcademicEvent) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM eventconstancy WHERE idAcademicEvent=?";
        Connection connection = null;
        EventConstancy eventConstancy;
        List<EventConstancy> eventConstanciesList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idAcademicEvent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String idEventConstancy = resultSet.getString("idEventConstancy");
                String recognitionType = resultSet.getString("recognitionType");
                String description = resultSet.getString("description");
                String emailAssistant = resultSet.getString("emailAssistant");

                eventConstancy = new EventConstancy(idEventConstancy, recognitionType, description, emailAssistant, idAcademicEvent);
                eventConstanciesList.add(eventConstancy);
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

        return eventConstanciesList;
    }

    public List<String> foundRegulatoryNotesByIdEventConstancy(String idEventConstancy) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT regulatoryNote FROM `regulatorynotes-eventconstancy` WHERE idEventConstancy=?";
        Connection connection = null;
        List<String> regulatoryNotesList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idEventConstancy);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String regulatoryNote = resultSet.getString("regulatoryNote");
                regulatoryNotesList.add(regulatoryNote);
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

        return regulatoryNotesList;
    }

    public List<Member> foundValidatorsByIdEventConstancy(String idEventConstancy) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT curpMember FROM `validators-eventconstancy` WHERE idEventConstancy=?";
        Connection connection = null;
        MemberDAO memberDAO = new MemberDAO();
        List<Member> validatorSList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idEventConstancy);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String validatorCurp = resultSet.getString("curpMember");
                Member validator = memberDAO.findMemberByCurp(validatorCurp);
                validatorSList.add(validator);
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

        return validatorSList;
    }

    public EventConstancy foundEventConstancyByIdEventConstancy(String idEventConstancy) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM eventconstancy WHERE idEventConstancy=?";
        Connection connection = null;
        EventConstancy eventConstancy=null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, idEventConstancy);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recognitionType = resultSet.getString("recognitionType");
                String description = resultSet.getString("description");
                String emailAssistant = resultSet.getString("emailAssistant");
                String idAcademicEvent = resultSet.getString("idAcademicEvent");

                eventConstancy = new EventConstancy(idEventConstancy, recognitionType, description, emailAssistant, idAcademicEvent);
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

        return eventConstancy;
    }


    public boolean addEventConstancy(EventConstancy eventConstancy) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO eventconstancy (`idEventConstancy`, `recognitionType`, `description`," +
                " `emailAssistant`, `idAcademicEvent`) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idEventConstancy = "EVC-"+ getLastIdNumber("idEventConstancy", "eventconstancy");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idEventConstancy);
            statement.setString(2, eventConstancy.getRecognitionType());
            statement.setString(3, eventConstancy.getDescription());
            statement.setString(4, eventConstancy.getEmailAssistant());
            statement.setString(5, eventConstancy.getIdAcademicEvent());
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

    public boolean addRegulatoryNoteToEventConstancy(String idEventConstancy, String regulatoryNote) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO `regulatorynotes-eventconstancy` (`idRegulatoryNoteEventConstancy`," +
                " `idEventConstancy`, `regulatoryNote`) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idRegulatoryNoteEventConstancy = "RNC-"+ getLastIdNumber("idRegulatoryNoteEventConstancy",
                "`regulatorynotes-eventconstancy`");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idRegulatoryNoteEventConstancy);
            statement.setString(2, idEventConstancy);
            statement.setString(3, regulatoryNote);
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

    public boolean addValidatorToEventConstancy(String idEventConstancy, String curpMember) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO `validators-eventconstancy` (`idValidatorEventConstancy`," +
                " `idEventConstancy`, `curpMember`) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        String idValidatorEventConstancy = "VDC-"+ getLastIdNumber("idValidatorEventConstancy", "`validators-eventconstancy`");

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, idValidatorEventConstancy);
            statement.setString(2, idEventConstancy);
            statement.setString(3, curpMember);
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

    public boolean addEventAssistant(EventAssistant eventAssistant) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO eventassistant (`email`, `name`) VALUES (?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, eventAssistant.getEmail());
            statement.setString(2, eventAssistant.getName());
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

    public EventAssistant foundEventAssistantByEmail(String email) throws BusinessLogicException{

        final String SQL_SELECT = "SELECT eventassistant.name FROM eventassistant WHERE `email` = ?";
        Connection connection = null;
        EventAssistant eventAssistant = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                String name = resultSet.getString("name");

                eventAssistant = new EventAssistant(email, name);
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

        return eventAssistant;
    }

    public boolean generatePDFEventConstancy(EventConstancy eventConstancy, int numerOfConstancyDownload) throws BusinessLogicException{

        Document document = new Document();
        boolean operationResult = false;
        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(70.86f, 70.86f, 56.69f, 2.83f);

        try {

            String route = System.getProperty("user.home");
            PdfWriter.getInstance(document, new FileOutputStream(route + "/Desktop/Constancy-" + numerOfConstancyDownload + ".pdf"));
            Image logoUV = Image.getInstance("src/user/images/LogoUV.png");
            int widthLogoUV = 670;
            int heightLogoUV = 570;
            int absolutePositionXLogoUV = -165;
            int absolutePositionYLogoUV = 10;
            logoUV.scaleToFit(widthLogoUV, heightLogoUV);
            logoUV.setAbsolutePosition(absolutePositionXLogoUV, absolutePositionYLogoUV);

            document.open();

            BaseFont titleBaseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, true);
            Font titleFont = new Font(titleBaseFont, 20f, Font.BOLD, BaseColor.BLACK);

            BaseFont headerBaseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, true);
            Font headerFont = new Font(headerBaseFont, 16f, Font.BOLD, BaseColor.BLACK);

            BaseFont contentsBaseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, true);
            Font contentsFont = new Font(contentsBaseFont, 14f, Font.BOLD, BaseColor.BLACK);

            Paragraph titleParagraph = createNewCenterParagraph();
            titleParagraph.setFont(titleFont);
            titleParagraph.add("Universidad Veracruzana\n\n");

            Paragraph headerParagraph = createNewCenterParagraph();
            headerParagraph.setFont(headerFont);
            headerParagraph.add("Otorga la presente\n\n");
            headerParagraph.add("Constancia a:\n\n\n\n");

            Paragraph eventAssistantNameParagraph = createNewCenterParagraph();
            eventAssistantNameParagraph.setFont(headerFont);
            EventAssistant eventAssistant = foundEventAssistantByEmail(eventConstancy.getEmailAssistant());
            eventAssistantNameParagraph.add(eventAssistant.getName() + "\n\n\n\n");

            int numberMaxOfCharacters = 100;
            Paragraph descriptionParagraph;
            if (eventConstancy.getDescription().length() > numberMaxOfCharacters) {
                descriptionParagraph = new Paragraph();
                descriptionParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                descriptionParagraph.setFont(contentsFont);
                descriptionParagraph.add(eventConstancy.getDescription() + "\n\n\n\n");
            } else {
                descriptionParagraph = createNewCenterParagraph();
                descriptionParagraph.setFont(contentsFont);
                descriptionParagraph.add(eventConstancy.getDescription() + "\n\n\n\n");
            }

            List<Member> validatorsList = foundValidatorsByIdEventConstancy(eventConstancy.getIdEventConstancy());

            Paragraph validatorNamesParagraph = createNewCenterParagraph();
            validatorNamesParagraph.setFont(contentsFont);
            validatorNamesParagraph.setTabSettings(new TabSettings(100f));

            int numberOfValidatorNamesPerLine = 0;
            for (Member validator : validatorsList) {
                if (numberOfValidatorNamesPerLine < 2) {
                    validatorNamesParagraph.add(validator.getName());
                    validatorNamesParagraph.add(Chunk.TABBING);
                } else {
                    validatorNamesParagraph.add("\n\n\n");
                    validatorNamesParagraph.add(validator.getName());
                    validatorNamesParagraph.add(Chunk.TABBING);
                    numberOfValidatorNamesPerLine = 0;
                }
                numberOfValidatorNamesPerLine++;
            }

            validatorNamesParagraph.add("\n\n\n\n");

            Paragraph regulatoryNotesParagraph = new Paragraph();
            regulatoryNotesParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            regulatoryNotesParagraph.setFont(contentsFont);

            List<String> regulatoryNotesList = foundRegulatoryNotesByIdEventConstancy(eventConstancy.getIdEventConstancy());

            for (String regulatoryNote : regulatoryNotesList) {
                regulatoryNotesParagraph.add("- ");
                regulatoryNotesParagraph.add(regulatoryNote);
                regulatoryNotesParagraph.add("\n");
            }

            document.add(logoUV);
            document.add(titleParagraph);
            document.add(headerParagraph);
            document.add(eventAssistantNameParagraph);
            document.add(descriptionParagraph);
            document.add(validatorNamesParagraph);
            document.add(regulatoryNotesParagraph);

            operationResult = true;

        }catch (SQLException sqlException) {
            throw new BusinessLogicException("ConnectionException", sqlException);
        }catch (FileNotFoundException ex){
            Logger.getLogger(EventAssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EventAssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch (DocumentException ex) {
            Logger.getLogger(EventAssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            document.close();
        }
        return operationResult;
    }

    public Paragraph createNewCenterParagraph(){

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        return paragraph;
    }




}
