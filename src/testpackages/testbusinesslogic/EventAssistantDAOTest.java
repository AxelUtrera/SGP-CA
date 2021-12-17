package testpackages.testbusinesslogic;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import businesslogic.BusinessLogicException;
import businesslogic.EventAssistantDAO;
import controller.AlertInterface;
import domain.EventAssistant;
import domain.EventConstancy;
import domain.Member;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EventAssistantDAOTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllEventConstanciesByIdAcademicEventSuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<EventConstancy> eventConstanciesExpected = new ArrayList<>();
        String idAcademicEvent = "EVT-1";
        EventConstancy eventConstancy = new EventConstancy("EVC-1", "Asistencia",
                "Por la organización del taller de microservicios", "S19013985@estudiantes.uv.mx", idAcademicEvent);
        eventConstanciesExpected.add(eventConstancy);
        try {
            List<EventConstancy> eventConstanciesResult = eventAssistantDAO.displayAllEventConstanciesByIdAcademicEvent(idAcademicEvent);
            assertEquals("Prueba obtener todas las constancias de evento asociadas a un idAcademicEvent",
                    eventConstanciesExpected, eventConstanciesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllEventConstanciesByIdAcademicEventFailedTest() {

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<EventConstancy> eventConstanciesExpected = new ArrayList<>();
        String idAcademicEvent = "EVT-100";
        try {
            List<EventConstancy> eventConstanciesResult = eventAssistantDAO.displayAllEventConstanciesByIdAcademicEvent(idAcademicEvent);
            assertEquals("Prueba alterna con lista vacia de constancias de evento asociadas a un idAcademicEvent",
                    eventConstanciesExpected, eventConstanciesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundRegulatoryNotesByIdEventConstancySuccessfulTest(){

        String idEventConstancy = "EVC-1";
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<String> regulatoryNotesExpected = new ArrayList<>();
        String regulatoryNote = "nota reglamentaria";
        regulatoryNotesExpected.add(regulatoryNote);
        try {
            List<String> regulatoryNoteResult = eventAssistantDAO.foundRegulatoryNotesByIdEventConstancy(idEventConstancy);
            assertEquals("Prueba obtener todas las notas reglamentarias asociadas a un idEventConstancy",
                    regulatoryNotesExpected, regulatoryNoteResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundRegulatoryNotesByIdEventConstancyFailedTest(){

        String idEventConstancy = "EVC-100";
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<String> regulatoryNotesExpected = new ArrayList<>();
        try {
            List<String> regulatoryNoteResult = eventAssistantDAO.foundRegulatoryNotesByIdEventConstancy(idEventConstancy);
            assertEquals("Prueba alterna con lista vacia de notas reglamentarias asociads a un idEventConstancy",
                    regulatoryNotesExpected, regulatoryNoteResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundValidatorsByIdEventConstancySuccessfulTest(){

        String idEventConstancy = "EVC-1";
        Date date =  new Date(1985,6,3);
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<Member> validatorsExpected = new ArrayList<>();
        Member validator = new Member("SASO750909HDFNNS05","Cortés Verdín María Karen",
                date, "himno@gmail.com", "2281776699",
                "Responsable", "AGP-1", "123");
        validatorsExpected.add(validator);
        try {
            List<Member> validatorsResult = eventAssistantDAO.foundValidatorsByIdEventConstancy(idEventConstancy);
            assertEquals("Prueba obtener todos los nombres de validadores asociados a un idEventConstancy", validatorsExpected, validatorsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundValidatorNamesByIdEventConstancyFailedTest(){

        String idEventConstancy = "EVC-100";
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        List<Member> validatorsExpected = new ArrayList<>();
        try {
            List<Member> validatorsResult = eventAssistantDAO.foundValidatorsByIdEventConstancy(idEventConstancy);
            assertEquals("Prueba alterna con lista vacia de nombres de validadores asociados a un idEventConstancy",
                    validatorsExpected, validatorsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundEventConstancyByIdEventConstancySuccessfulTest(){

        String idEventConstancy = "EVC-1";
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        EventConstancy eventConstancyExpected = new EventConstancy("EVC-1", "Asistencia",
                "Por la organización del taller de microservicios", "S19013985@estudiantes.uv.mx", "EVT-1");
        try {
            EventConstancy eventConstancyResult = eventAssistantDAO.foundEventConstancyByIdEventConstancy(idEventConstancy);
            assertEquals("Prueba obtener la informacion de una constancia de evento asociados a su IdEventConstancy",
                    eventConstancyExpected, eventConstancyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundEventConstancyByIdEventConstancyFailedTest(){

        String idEventConstancy = "EVC-100";
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        try {
            EventConstancy eventConstancyResult = eventAssistantDAO.foundEventConstancyByIdEventConstancy(idEventConstancy);
            assertNull("Prueba alterna con objeto nulo de la informacion de una constancia de evento asociados a un IdEventConstancy",
                    eventConstancyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addEventConstancySuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        EventConstancy eventConstancyExpected = new EventConstancy("Asistencia",
                "Por la organización del taller de microservicios", "S19013985@estudiantes.uv.mx", "EVT-1");
        try {
            boolean eventConstancyAddedResult = eventAssistantDAO.addEventConstancy(eventConstancyExpected);
            assertTrue("Prueba insertar una constancia de evento correctamente", eventConstancyAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addEventAssistantSuccessfulTest(){
        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        EventAssistant eventAssistant = new EventAssistant("maestra@uv.mx", "Maria Karen Cortes");
        try {
            boolean eventAssistantAddedResult = eventAssistantDAO.addEventAssistant(eventAssistant);
            assertTrue("Prueba insertar un asistente de evento correctamente", eventAssistantAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdEventConstancyNumberSuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        int idEventConstancyExpected = 2;
        try {
            int idEventConstancyResult = eventAssistantDAO.getLastIdNumber("idEventConstancy", "eventconstancy");
            assertEquals("Prueba obtener el numero entero de la ultima idEventConstancy", idEventConstancyExpected, idEventConstancyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdRegulatoryNoteEventConstancyNumberSuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        int idRegulatoryNoteEventConstancyExpected = 2;
        try {
            int idRegulatoryNoteEventConstancyResult = eventAssistantDAO.getLastIdNumber("idRegulatoryNoteEventConstancy",
                    "`regulatorynotes-eventconstancy`");
            assertEquals("Prueba obtener el numero entero de la ultima idRegulatoryNoteEventConstancy",
                    idRegulatoryNoteEventConstancyExpected, idRegulatoryNoteEventConstancyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdValidatorEventConstancyNumberSuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        int idValidatorEventConstancyExpected = 2;
        try {
            int idValidatorEventConstancyResult = eventAssistantDAO.getLastIdNumber("idValidatorEventConstancy",
                    "`validators-eventconstancy`");
            assertEquals("Prueba obtener el numero entero de la ultima idValidatorEventConstancy", idValidatorEventConstancyExpected,
                    idValidatorEventConstancyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addRegulatoryNoteToEventConstancySuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        String idEventConstancy = "EVC-1";
        String regulatoryNote = "nota reglamentaria";
        try {
            boolean regulatoryNoteAddedResult = eventAssistantDAO.addRegulatoryNoteToEventConstancy(idEventConstancy, regulatoryNote);
            assertTrue("Prueba insertar una nota regulatoria a una constancia de evento correctamente", regulatoryNoteAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void addValidatorToEventConstancySuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        String idEventConstancy = "EVC-1";
        String validatorCURP = "SASO750909HDFNNS05";
        try {
            boolean validatorAddedResult = eventAssistantDAO.addValidatorToEventConstancy(idEventConstancy, validatorCURP);
            assertTrue("Prueba insertar un validador a una constancia de evento correctamente", validatorAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void generatePDFEventConstancySuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        EventConstancy eventConstancyExpected = new EventConstancy("Asistencia",
                "Por la organización del taller de microservicios", "S19013985@estudiantes.uv.mx", "EVT-1");

        boolean fileExist = true;
        String route = System.getProperty("user.home");
        int numberOfConstancyDownload = 1;
        boolean generatedConstancyResult = false;

        do {
            File file = new File(route + "/Desktop/Constancy-"+numberOfConstancyDownload+".pdf");
            if (file.exists()) {
                numberOfConstancyDownload++;
            } else {
                try {
                    generatedConstancyResult = eventAssistantDAO.generatePDFEventConstancy(eventConstancyExpected, numberOfConstancyDownload);
                    fileExist = false;
                }catch(BusinessLogicException ex){
                    ALERT_INTERFACE.openAlertFailedInsert();
                }
            }
        }while(fileExist);

        assertTrue("Prueba generar una constancia de evento correctamente", generatedConstancyResult);

    }

    @Test
    public void foundEventAssistantByEmailSuccessfulTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        String eventAssistantEmail = "maestra@uv.mx";
        EventAssistant eventAssistantExpected = new EventAssistant(eventAssistantEmail, "Maria Karen Cortes");
        try {
            EventAssistant eventAssistantResult = eventAssistantDAO.foundEventAssistantByEmail(eventAssistantEmail);
            assertEquals("Prueba obtener un asistente de evento por su correo", eventAssistantExpected, eventAssistantResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundEventAssistantByEmailFailedTest(){

        EventAssistantDAO eventAssistantDAO = new EventAssistantDAO();
        String eventAssistantEmail = "asha@uv.mx";
        try {
            EventAssistant eventAssistantResult = eventAssistantDAO.foundEventAssistantByEmail(eventAssistantEmail);
            assertNull("Prueba alterna obteniendo un asistente de evento nulo por un correo inexistente", eventAssistantResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

}
