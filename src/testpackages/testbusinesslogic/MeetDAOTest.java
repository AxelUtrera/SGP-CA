package testpackages.testbusinesslogic;

import businesslogic.MeetDAO;
import domain.Meet;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MeetDAOTest {

    private Date date =  new Date(21,12,02);
    private Time hour = new Time(11,56,56);
    private Meet meetExpected1 = new Meet("MET-01",date,"Asignar labores entre los miembros del CA", hour,"IA in the world", "Auditorio de la escuela", "MEM-01");
    private Meet meetExpected2 = new Meet("MET-02",date,"Asignar labores entre los miembros del CA", hour,"IA in the world", "Auditorio de la escuela","MEM-01");
    private MeetDAO meetDAO = new MeetDAO();

    private void insertDataToDataBase(){
        meetDAO = new MeetDAO();
        meetDAO.addMeet(meetExpected1);
        meetDAO.addMeet(meetExpected2);
    }

    private List<Meet> insertToList(){
        List<Meet> eventsExpected = new ArrayList<>();
        eventsExpected.add(meetExpected1);
        eventsExpected.add(meetExpected2);
        return eventsExpected;
    }

    @Test
    public void addMeetSuccessfulTest(){
        boolean meetInsertedResult = meetDAO.addMeet(meetExpected1);
        assertTrue("Prueba con una insercion de una reunion exitosa",meetInsertedResult);
    }

    @Test
    public void findMeetByIdSuccessfulTest(){
        meetDAO.addMeet(meetExpected1);
        Meet meetResult = meetDAO.findMeetById("MET-01");
        assertEquals( meetExpected1.getIdMeet(), meetResult.getIdMeet());
    }

    @Test
    public void findMeetByIdFailedTest(){
        Meet meetResult = meetDAO.findMeetById("MEET-01");
        assertNull("prueba con id inexistente", meetResult);
    }

    @Test
    public void displayAllMeetsSuccessfulTest() {
        insertDataToDataBase();
        List<Meet> meetResult = meetDAO.displayAllMeets();
        assertEquals("el test pasa si las listas son iguales", insertToList(), meetResult);
    }

    @Test
    public void displayAllMeetsFailedTest(){
        List<Meet>  meetExpected = new ArrayList<>();
        List<Meet> meetResult = meetDAO.displayAllMeets();
        assertEquals("Prueba con listas vacias", meetExpected, meetResult);
    }

    @Test
    public void editMeetByIdSuccessfulTest(){
        Meet meet = new Meet("MET-01",date,"labores entre los miembros del CA", hour,"IA", "Auditorio de la escuela", null);
        boolean meetResult = meetDAO.updateMeetById(meet);
        assertTrue("prueba para edicion de reunion existente", meetResult);
    }

    @Test
    public void editMeetByIdFailedTest(){
        Meet meet = new Meet("MEET-01",date,"labores entre los miembros del CA", hour,"IA", "Auditorio de la escuela", null);
        boolean meetResult = meetDAO.updateMeetById(meet);
        assertFalse("prueba para edicion de reunion existente", meetResult);
    }


    @Test
    public void deleteMeetByIdSuccessfulTest(){
        boolean meetResult = meetDAO.deleteMeetById("MET-01");
        meetDAO.deleteMeetById("MET-02");
        assertTrue("Prueba para la eliminacion de una reunion existente", meetResult);
    }

    @Test
    public void deteleMeetByIdFailedTest(){
        boolean meetResult = meetDAO.deleteMeetById("MEET-01");
        assertFalse("Prueba para la emliminacion de una reunion inexistente", meetResult);
    }

    @Test
    public void getLastIdEventNumberSuccessfulTest(){
        //Se define el numero esperado como si la base de datos estuviera vacia.
        int meetNumbIdExpected = 1;
        int meetNumbIdResult = meetDAO.getLastIdMeetNumber();
        assertEquals("Prueba para obtener el numero mayor de las reuniones mas 1", meetNumbIdExpected, meetNumbIdResult);
    }

}
