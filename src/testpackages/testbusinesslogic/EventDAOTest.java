package testpackages.testbusinesslogic;

import businesslogic.BusinessLogicException;
import businesslogic.EventDAO;
import controller.AlertInterface;
import domain.Event;
import org.junit.Test;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class EventDAOTest {

    private Date date =  new Date(2021-1922,01,02);
    private Time hour = new Time(21,56,56);
    private Event eventExpected1 = new Event("exposicion1", "Evento 1", hour , "facultad de estadistica e informatica", date);
    private Event eventExpected2 = new Event("exposicion2", "Evento 1", hour, "facultad de estadistica e informatica", date);
    private EventDAO EVENT_DAO = new EventDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    private void insertDataToDataBase(){
        EVENT_DAO = new EventDAO();
        try {
            EVENT_DAO.addEvent(eventExpected1);
            EVENT_DAO.addEvent(eventExpected2);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }


    @Test
    public void addEventSuccessfulTest()throws SQLException{
        boolean eventResult = EVENT_DAO.addEvent(eventExpected1);
        assertTrue("Agregacion de un nuevo evento", eventResult);
    }


    @Test
    public void findEventByIdSuccessfulTest() throws SQLException{
        Event eventExpected = new Event("EVT-1","exposicion1", "Evento 1", hour , "facultad de estadistica e informatica", date);
        Event eventResult = EVENT_DAO.findEventById("EVT-1");
        assertEquals("Se comparan que los dos id sean los mismos",eventExpected, eventResult);
    }

    @Test
    public void findEventFailedTest() throws SQLException{
        String idEvent = "EVNT-1";
        Event eventResult = EVENT_DAO.findEventById(idEvent);
        assertNull("Prueba con id inexistente", eventResult);
    }

    @Test
    public void DisplayAllEventsSuccessfulTest() throws SQLException {
        insertDataToDataBase();
        List<Event> eventExpected = new ArrayList<>();
        Event eventExpected1 = new Event("EVT-1","exposicion1", "Evento 1", hour , "facultad de estadistica e informatica", date);
        Event eventExpected2 = new Event("EVT-2","exposicion2", "Evento 1", hour, "facultad de estadistica e informatica", date);
        eventExpected.add(eventExpected1);
        eventExpected.add(eventExpected2);
        List<Event> eventResult = EVENT_DAO.displayAllEvents();
        assertEquals("Los datos de la lista y la base deben ser los mismos",eventExpected, eventResult);
    }

    @Test
    public void displayAllEventsFailedTest() throws SQLException{
        List<Event> eventExpected = new ArrayList<>();
        List<Event> eventResult = EVENT_DAO.displayAllEvents();
        assertEquals("Prueba con base de datos vacia", eventExpected, eventResult);
    }

    @Test
    public void updateEventByIdSuccessfulTest(){
        Event eventToModify = new Event("EVT-1", "name modify", "Event 45", hour , "nido del alcon", date);
        try {
            boolean eventResult = EVENT_DAO.updateEventById(eventToModify);
            assertTrue("Modificacion de evento con retorno true", eventResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateEventByIdFailedTest(){
        Event eventToModify = new Event("EVENT-1", "name modify", "Event 45", hour , "nido del alcon", date);
        try {
            boolean eventResult = EVENT_DAO.updateEventById(eventToModify);
            assertFalse("Modificacion de evento con retorno true", eventResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deleteEventByIdSuccessfulTest(){
        try {
            boolean eventResult = EVENT_DAO.deleteEventById("EVT-1");
            EVENT_DAO.deleteEventById("EVT-2");
            assertTrue("Eliminacion de evento existente", eventResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deleteEventByIdFailedTest(){
        try {
            boolean eventResult = EVENT_DAO.deleteEventById("MET-1");
            assertTrue("Eliminacion de evento con id no existente", eventResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdEventNumberSuccessfulTest(){

        int eventNumbIdExpected = 1;
        try {
            int eventNumbIdResult = EVENT_DAO.getLastIdEventNumber();
            assertEquals("Prueba para obtener el numero mayor de los eventos mas 1", eventNumbIdExpected, eventNumbIdResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }


}
