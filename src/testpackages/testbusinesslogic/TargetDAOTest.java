package testpackages.testbusinesslogic;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import businesslogic.BusinessLogicException;
import businesslogic.TargetDAO;
import controller.AlertInterface;
import domain.Target;
import org.junit.Test;

public class TargetDAOTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void addTargetSuccessfulTest(){

        TargetDAO targetDAO = new TargetDAO();
        Target targetExpected = new Target( "Mediante las juntas de cuerpo academico se acordó colegiadamente la" +
                " necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor",
                "Incrementar el numero de integrantes del CA", "Cumplido", "P_LIS_UVitsCA");
        try {
            boolean targetsAddedResult = targetDAO.addTarget(targetExpected);
            assertTrue("Prueba insertar un objetivo correctamente", targetsAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdTargetNumberSuccessfulTest(){

        TargetDAO targetDAO = new TargetDAO();
        int idTargetExpected = 2;
        try {
            int idTargetResult = targetDAO.getLastIdTargetNumber();
            assertEquals("Prueba obtener el numero entero del ultimo idTarget", idTargetExpected, idTargetResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateStrategySuccessfulTest(){

        TargetDAO targetDAO = new TargetDAO();
        String idTarget = "TRG-1";
        try {
            Target target = targetDAO.foundTargetByIdTarget(idTarget);
            target.setDescription("Incrementar la gestión académica individual y colectiva de los integrantes del CA");
            target.setTitle("Incremento de la gestión académica");
            target.setStatus("Cumplido");
            target.setKeyWorkplan("P_LIS_UVitsCA");

            boolean updatedTargetsResult = targetDAO.updateTarget(target);
            assertTrue("Prueba actualizar un objetivo correctamente", updatedTargetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundTargetByIdTargetSuccessfulTest(){
        String idTarget = "TRG-1";
        TargetDAO targetDAO = new TargetDAO();
        Target targetExpected = new Target(idTarget, "Mediante las juntas de cuerpo academico se acordó colegiadamente" +
                " la necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor",
                "Incrementar el numero de integrantes del CA", "Cumplido","P_LIS_UVitsCA");
        try {
            Target targetResult = targetDAO.foundTargetByIdTarget(idTarget);
            assertEquals("Prueba verificar que un objetivo exista", targetExpected, targetResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundTargetByIdTargetFailedTest(){
        String idTarget = "TRG-100";
        TargetDAO targetDAO = new TargetDAO();
        try{
            assertNull("Prueba alterna, no existe el objetivo", targetDAO.foundTargetByIdTarget(idTarget));
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundTargetsByWorkplanKeySuccessfulTest(){

        TargetDAO targetDAO = new TargetDAO();
        String workplanKey = "P_LIS_UVitsCA";
        List<Target> targetsExpected = new ArrayList<>();
        Target targetExpected = new Target("TRG-1", "Mediante las juntas de cuerpo academico se acordó colegiadamente" +
                " la necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor",
                "Incrementar el numero de integrantes del CA", "Cumplido", workplanKey);
        targetsExpected.add(targetExpected);
        try {
            List<Target> targetsResult = targetDAO.foundTargetsByKeyWorkplan(workplanKey);
            assertEquals("Prueba obtener todas los objetivos asociados a un workplankey", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundTargetsByWorkplanKeyFailedTest(){

        TargetDAO targetDAO = new TargetDAO();

        String keyWorkplan="P_LIS_UVitsCAAA";
        List<Target> targetsExpected = new ArrayList<>();
        try {
            List<Target> targetsResult = targetDAO.foundTargetsByKeyWorkplan(keyWorkplan);
            assertEquals("Prueba alterna con lista vacia de objetivos asociados a un keyWorkplan inexistente", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundPendingTargetsByKeyWorkplanSuccessfulTest(){

        String keyWorkplan="P_LIS_UVitsCA";
        TargetDAO targetDAO = new TargetDAO();
        List<Target> targetsExpected = new ArrayList<>();
        Target targetExpected = new Target("TRG-1", "Mediante las juntas de cuerpo academico se acordó colegiadamente" +
                " la necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor",
                "Incrementar el numero de integrantes del CA", "Pendiente","P_LIS_UVitsCA");
        targetsExpected.add(targetExpected);
        try {
            List<Target> targetsResult = targetDAO.foundPendingTargetsByKeyWorkplan(keyWorkplan);
            assertEquals("Prueba obtener todos los objetivos pendientes asociados a un keyWorkplan", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundPendingTargetsByKeyWorkplanFailedTest(){

        String keyWorkplan="P_LIS_UVitsCA";
        TargetDAO targetDAO = new TargetDAO();
        List<Target> targetsExpected = new ArrayList<>();
        try {
            List<Target> targetsResult = targetDAO.foundPendingTargetsByKeyWorkplan(keyWorkplan);
            assertEquals("Prueba alterna con lista vacia de objetivos pendientes asociados a un keyWorkPlan", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundAchievedTargetsByKeyWorkplanSuccessfulTest(){

        String keyWorkplan="P_LIS_UVitsCA";
        TargetDAO targetDAO = new TargetDAO();
        List<Target> targetsExpected = new ArrayList<>();
        Target targetExpected = new Target("TRG-1", "Mediante las juntas de cuerpo academico se acordó colegiadamente" +
                " la necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor",
                "Incrementar el numero de integrantes del CA", "Cumplido","P_LIS_UVitsCA");
        targetsExpected.add(targetExpected);
        try {
            List<Target> targetsResult = targetDAO.foundAchievedTargetsByKeyWorkplan(keyWorkplan);
            assertEquals("Prueba obtener todos los objetivos cumplidos asociados a un keyWorkPlan", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundAchievedTargetsByKeyWorkplanFailedTest(){

        String keyWorkplan="P_LIS_UVitsCA";
        TargetDAO targetDAO = new TargetDAO();
        List<Target> targetsExpected = new ArrayList<>();
        try {
            List<Target> targetsResult = targetDAO.foundAchievedTargetsByKeyWorkplan(keyWorkplan);
            assertEquals("Prueba alterna con lista vacia de objetivos cumplidos asociados a un keyWorkPlan", targetsExpected, targetsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }


}
