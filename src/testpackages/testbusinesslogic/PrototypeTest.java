package testpackages.testbusinesslogic;

import businesslogic.BusinessLogicException;
import businesslogic.PrototypeDAO;
import controller.AlertInterface;
import domain.Prototype;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PrototypeTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllPrototypesSuccessfulTest(){ 

        PrototypeDAO prototypeDAO = new PrototypeDAO();
        List<Prototype> PrototypesExpected = new ArrayList<>();
        Prototype prototypeExpected1 = new Prototype("Prototype", "Prototipo","Maestria", "IVP-1","PRT-1");
        Prototype prototypeExpected2 = new Prototype("Prototype", "Prestamo","Maestria", "IVP-1","PRT-2");
        PrototypesExpected.add(prototypeExpected1);
        PrototypesExpected.add(prototypeExpected2);
        try{
            List<Prototype> PrototypesResult = prototypeDAO.displayAllPrototypes();

            assertEquals("Prueba obtener todas los prototipos existentes", PrototypesExpected, PrototypesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllPrototypesFailedTest(){

        PrototypeDAO prototypeDAO = new PrototypeDAO();

        List<Prototype> PrototypesExpected = new ArrayList<>();
        try{
            List<Prototype> PrototypesResult = prototypeDAO.displayAllPrototypes();

            assertEquals("Prueba alterna con lista vacia de prototipos", PrototypesExpected, PrototypesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addPrototypesuccessfulTest(){

        Prototype prototypeExpected = new Prototype("Prototype", "Prototipo","Maestria", "IVP-1","PRT-1");
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        try{
            boolean PrototypesAddedResult = prototypeDAO.addPrototype(prototypeExpected);
            assertTrue(PrototypesAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }
    @Test
    public void addPrototypesuccessfulTest2(){

        Prototype prototypeExpected = new Prototype("Prototype", "Prestamo","Maestria", "IVP-1","PRT-2");;
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        try{
            boolean PrototypesAddedResult = prototypeDAO.addPrototype(prototypeExpected);
            assertTrue(PrototypesAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updatePrototypeTest(){

        PrototypeDAO prototypeDAO = new PrototypeDAO();
        String idprototype = "PRT-1";
        try{
            Prototype prototype = prototypeDAO.foundPrototypeById(idprototype);
            prototype.setTitle("Contruccion2");
            boolean updatedPrototypesResult = prototypeDAO.updatePrototype(prototype);
            assertTrue(updatedPrototypesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deletePrototypeSuccessfulTest(){

        String idPrototype = "PRT-1";
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        try{
            boolean PrototypesDeletedResult = prototypeDAO.deletePrototype(idPrototype);
            assertTrue(PrototypesDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deletePrototypeFailedTest(){

        String idPrototype = "IVP-040";
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        try{
            boolean PrototypesDeletedResult = prototypeDAO.deletePrototype(idPrototype);
            assertTrue(PrototypesDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundPrototypeByIDSuccessfulTest(){
        String idPrototype = "PRT-1";
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        Prototype prototypeExpected = new Prototype("Prototype", "Prototipo","Maestria", "IVP-1","PRT-1");
        try{
            Prototype prototypeResult = prototypeDAO.foundPrototypeById(idPrototype);
            assertEquals("Prueba verificar que un prototipo exista", prototypeExpected.getIdPrototype(), prototypeResult.getIdPrototype());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundPrototypeByIDFailedTest(){
        String idPrototype = "IVP-065";
        PrototypeDAO prototypeDAO = new PrototypeDAO();
        try{
            assertNull("Prueba alterna, no existe el prototipo", prototypeDAO.foundPrototypeById(idPrototype));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
