package testpackages.testbusinesslogic;

import businesslogic.MemorandumDAO;
import domain.Memorandum;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MemorandumDAOTest {

    private Date date =  new Date(21,12,02);
    private Time hour = new Time(11,56,56);
    private Memorandum memorandumExpected1 = new Memorandum("MEM-01",hour,"Asignar labores entre los miembros del CA", date,"IA in the world");
    private Memorandum memorandumExpected2 = new Memorandum("MEM-02",hour,"Asignar labores entre los miembros del CA", date,"IA in the world");
    private MemorandumDAO memorandumDAO= new MemorandumDAO();

    private void insertDataToDataBase(){
        memorandumDAO.addMemorandum(memorandumExpected1);
        memorandumDAO.addMemorandum(memorandumExpected2);
    }

    private List<Memorandum> insertToList() {
        List<Memorandum> memorandumsExpected = new ArrayList<>();
        memorandumsExpected.add(memorandumExpected1);
        memorandumsExpected.add(memorandumExpected2);
        return memorandumsExpected;
    }

    @Test
    public void addMemorandumSuccessfullTest(){
        boolean memorandumInsertedResult = memorandumDAO.addMemorandum(memorandumExpected1);
        assertTrue("Prueba de insercion de minuta", memorandumInsertedResult);
    }

    @Test
    public void findMemorandumByIdSuccessfulTest(){
        Memorandum memorandumFindExpected = memorandumExpected1;
        Memorandum memorandumFindResult = memorandumDAO.findMemorandumById("MEM-01");
        assertEquals("los objetos son iguales cuando se encuentra en la base de datos por el id", memorandumFindExpected.getIdMemorandum(), memorandumFindResult.getIdMemorandum());
    }

    @Test
    public void findMemorandumByIdFailedTest(){
        Memorandum memorandumFindResult = memorandumDAO.findMemorandumById("MEMO-01");
        assertNull("Prueba de busqueda con id inexistente", memorandumFindResult);
    }


    @Test
    public void displayAllMemorandumsSuccessfulTest(){
        insertDataToDataBase();
        List<Memorandum> memorandumResult = memorandumDAO.displayAllMemorandums();
        assertEquals("El test es exitoso si las dos listas son iguales",insertToList(),memorandumResult);
    }

    @Test
    public void displayAllMemorandumsFailedTest(){
        List<Memorandum> memorandumExpected =  new ArrayList<>();
        List<Memorandum> memorandumResult = memorandumDAO.displayAllMemorandums();
        assertEquals("prueba de muestra con listas vacias",memorandumExpected,memorandumResult);
    }

    @Test
    public void deleteMemorandumsByIdSuccessfullTest(){
        boolean memorandumResult = memorandumDAO.deleteMemorandumById("MEM-01");
        memorandumDAO.deleteMemorandumById("MEM-02");
        assertTrue("prueba de eliminacion de minuta existente", memorandumResult);
    }

    @Test
    public void deleteMemorandumsByIdFailedTest(){
        boolean memorandumResult = memorandumDAO.deleteMemorandumById("MEMO-01");
        assertFalse("prueba de eliminacion de minuta inexistente", memorandumResult);
    }


}
