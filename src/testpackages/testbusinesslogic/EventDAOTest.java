package testpackages.testbusinesslogic;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

import businesslogic.EventDAO;
import domain.Event;

import org.junit.Test;


import static org.junit.Assert.*;

public class EventDAOTest {

    private Date date =  new Date(2021,12,02);
    private Time hour = new Time(12,56,56);
    private Event eventExpected1 = new Event("event-01", "exposicion1", "Evento 1", hour , "facultad de estadistica e informatica", date);
    private Event eventExpected2 = new Event("event-02", "exposicion2", "Evento 1", hour, "facultad de estadistica e informatica", date);
    private EventDAO eventDAO = new EventDAO();

    private void insertDataToDataBase(){
        EventDAO eventDAO = new EventDAO();
        eventDAO.insertOneEvent(eventExpected1);
        eventDAO.insertOneEvent(eventExpected2);
    }

    private List<Event> insertToList(){
        List<Event> eventsExpected = new ArrayList<>();
        eventsExpected.add(eventExpected1);
        eventsExpected.add(eventExpected2);
        return eventsExpected;
    }

    @Test
    public void founOneEventByIdSuccessfullTest() throws SQLException{
        eventDAO.insertOneEvent(eventExpected1);
        boolean eventResult = eventDAO.foundEventById("event-01");
        assertTrue("Devuelve true si se encontro el id", eventResult);
    }

    @Test
    public void DisplayAllEventsSuccessfullTest() throws SQLException {
        //duda sobre dependencia de codigo.
        insertDataToDataBase();
        List<Event> eventResult = eventDAO.displayAllEvents();
        assertEquals("Los datos de la lista y la base deben ser los mismos",insertToList(), eventResult);
    }

    @Test
    public void deleteEventByIdTest(){
        boolean eventResult = eventDAO.deleteEventById(eventExpected1);
        boolean eventResult1 = eventDAO.deleteEventById(eventExpected2);
        assertTrue("Regresa un true si la elimina el evento",eventResult);
    }




}
