package testpackages.testbusinesslogic;

import businesslogic.BusinessLogicException;
import businesslogic.ReceptionWorkDAO;
import controller.AlertInterface;
import domain.ReceptionWork;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ReceptionWorkTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllReceptionWorksSuccessfulTest(){ //FAIL

        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        List<ReceptionWork> receptionWorksExpected = new ArrayList<>();
        ReceptionWork receptionWorkExpected1 = new ReceptionWork("Reception Work", "Contruccion", "Doctorado", "IVP-1", "RPW-1", "Finalizado", "Katia Molina");
        ReceptionWork receptionWorkExpected2 = new ReceptionWork("Reception Work", "WorkPlan", "Doctorado", "IVP-1", "RPW-2", "Finalizado", "Katia Molina");
        receptionWorksExpected.add(receptionWorkExpected1);
        receptionWorksExpected.add(receptionWorkExpected2);
        try{
            List<ReceptionWork> receptionWorksResult = receptionWorkDAO.displayAllReceptionWorks();

            assertEquals("Prueba obtener todas los trabajos recepcionales existentes", receptionWorksExpected, receptionWorksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllReceptionWorksFailedTest(){

        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();

        List<ReceptionWork> receptionWorksExpected = new ArrayList<>();
        try{
            List<ReceptionWork> receptionWorksResult = receptionWorkDAO.displayAllReceptionWorks();

            assertEquals("Prueba alterna con lista vacia de trabajos recepcionales", receptionWorksExpected, receptionWorksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addReceptionWorkSuccessfulTest(){

        ReceptionWork receptionWorkExpected = new ReceptionWork("Reception Work", "Contruccion", "Doctorado", "IVP-1", "RPW-1", "Finalizado", "Katia Molina");
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try{
            boolean receptionWorksAddedResult = receptionWorkDAO.addReceptionWork(receptionWorkExpected);
            assertTrue(receptionWorksAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }
    @Test
    public void addReceptionWorkSuccessfulTest2(){

        ReceptionWork receptionWorkExpected = new ReceptionWork("Reception Work", "WorkPlan", "Doctorado", "IVP-1", "RPW-2", "Finalizado", "Katia Molina");
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try{
            boolean receptionWorksAddedResult = receptionWorkDAO.addReceptionWork(receptionWorkExpected);
            assertTrue(receptionWorksAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateReceptionWorkTest(){

        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        String id = "RPW-2";
        try{
            ReceptionWork receptionWork = receptionWorkDAO.foundReceptionWorkByID(id);
            receptionWork.setCondition("en proceso");
            boolean updatedReceptionWorksResult = receptionWorkDAO.updateReceptionWork(receptionWork);
            assertTrue(updatedReceptionWorksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteReceptionWorkSuccessfulTest(){

        String id = "RPW-1";
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try{
            boolean receptionWorksDeletedResult = receptionWorkDAO.deleteReceptionWork(id);
            assertTrue(receptionWorksDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteReceptionWorkFailedTest(){

        String id = "RPW-32";
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try{
            boolean receptionWorksDeletedResult = receptionWorkDAO.deleteReceptionWork(id);
            assertTrue(receptionWorksDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundReceptionWorkByIDSuccessfulTest(){
        String id = "RPW-2";
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ReceptionWork receptionWorkExpected = new ReceptionWork("Reception Work", "WorkPlan", "Doctorado", "IVP-1", "RPW-2", "Finalizado", "Katia Molina");
        try{
            ReceptionWork receptionWorkResult = receptionWorkDAO.foundReceptionWorkByID(id);
            assertEquals("Prueba verificar que un trabajo recepcional exista", receptionWorkExpected.getIdReceptionWork(), receptionWorkResult.getIdReceptionWork());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundReceptionWorkByCURPFailedTest(){
        String id = "RPW-32";
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try{
            assertNull("Prueba alterna, no existe el trabajo recepcional", receptionWorkDAO.foundReceptionWorkByID(id));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
