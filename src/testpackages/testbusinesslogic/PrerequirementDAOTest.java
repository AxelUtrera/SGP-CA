package testpackages.testbusinesslogic;

import businesslogic.PrerequirementDAO;
import domain.Prerequirement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PrerequirementDAOTest {
    Prerequirement prerequirementExpected1 = new Prerequirement( "PRE-1", "descripcion del primer prerequerimeinto","MOLK010131MNELPTA0", "MET-01");
    Prerequirement prerequirementExpected2 = new Prerequirement("PRE-02", "descripcion del segundo prerequerimeinto", "MET-02");
    PrerequirementDAO prerequirementDAO = new PrerequirementDAO();

    private void insertDataToDatabase(){
        prerequirementDAO.addPrerequirement(prerequirementExpected1);
        prerequirementDAO.addPrerequirement(prerequirementExpected2);
    }

    private List<Prerequirement> insertDataToList(){
        List<Prerequirement> prerequirementList = new ArrayList<>();
        prerequirementList.add(prerequirementExpected1);
        prerequirementList.add(prerequirementExpected2);
        return prerequirementList;
    }

    @Test
    public void addPrerequirementSuccessfulTest(){
        boolean prerequirementResult = prerequirementDAO.addPrerequirement(prerequirementExpected1);
        assertTrue("prueba de insercion de prerrequerimiento", prerequirementResult);
    }

    @Test
    public void displayAllPrerequirementsSuccessfultest(){
        List<Prerequirement> prerequerimientoExpected = insertDataToList();
        insertDataToDatabase();
        List<Prerequirement> prerequirementsResult = prerequirementDAO.displayAllPrerequirements();
        assertEquals("Prueba comparando dos listas, la de base de datos y la creada en este punto", prerequerimientoExpected,prerequirementsResult);
    }

    @Test
    public void displayAllPrerequirementsFailedTest(){
        List<Prerequirement> prerequirementsExpected = new ArrayList<>();
        List<Prerequirement> prerequirementsResult = prerequirementDAO.displayAllPrerequirements();
        assertEquals("Comparacion de listas vacias", prerequirementsExpected, prerequirementsResult);
    }

    @Test
    public void findPrerequirementByIdSuccessfulTest(){
        Prerequirement prerequirementResult = prerequirementDAO.findPrerequirementById("PRE-1");
        assertEquals("Prueba de busqueda con id existente", prerequirementExpected1, prerequirementResult);
    }

    @Test
    public void findPrerequirementByIdFailedTest(){
        Prerequirement prerequirementResult = prerequirementDAO.findPrerequirementById("PRERE-01");
        assertNull("Prueba de busqueda con id existente", prerequirementResult);
    }

    @Test
    public void updatePrerequirementSuceesfulTest(){
        prerequirementDAO.addPrerequirement(prerequirementExpected1);
        Prerequirement newPrerequirement = new Prerequirement("PRE-01", "Esta es la nueva descripcion", "MET-01");
        boolean prerequirementResult = prerequirementDAO.updatePrerequirement(newPrerequirement);
        assertTrue("Prueba de edicion de con  id existente", prerequirementResult);
    }

    @Test
    public void updatePrerequirementFailedTest(){
        Prerequirement newPrerequirement = new Prerequirement("PRERE-01", "Esta es la nueva descripcion", "MET-01");
        boolean prerequirementResult = prerequirementDAO.updatePrerequirement(newPrerequirement);
        assertFalse("Prueba de edicion de prerrequerimeinto con id inexistente", prerequirementResult);
    }

    @Test
    public void deletePrerequerientByIdSuccessfultest() {
        boolean prerequerimientResult = prerequirementDAO.deletePrerequirementById(prerequirementExpected1.getIdPrerequirement());
        prerequirementDAO.deletePrerequirementById(prerequirementExpected2.getIdPrerequirement());
        assertTrue("Prueba de eliminacion de con id existente", prerequerimientResult);
    }

    @Test
    public void deletePrerequirementByIdFailedTest(){
        boolean prerequerimientResult = prerequirementDAO.deletePrerequirementById("PRERE-01");
        assertFalse("Prueba de eliminacion de prerrequetimiento con id inexistente", prerequerimientResult);
    }

    @Test
    public void getLastIdEventNumberSuccessfulTest(){
        //Se define el numero esperado como si la base de datos estuviera vacia.
        int prerequirementNumbIdExpected = 1;
        int prerequirementNumbIdResult = prerequirementDAO.getLastIdPrerequirementNumber();
        assertEquals("Prueba para obtener el numero mayor de los prerrequerimeintos mas 1", prerequirementNumbIdExpected, prerequirementNumbIdResult);
    }
}
