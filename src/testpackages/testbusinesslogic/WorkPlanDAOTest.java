package testpackages.testbusinesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import businesslogic.BusinessLogicException;
import businesslogic.WorkPlanDAO;
import controller.AlertInterface;
import domain.WorkPlan;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class WorkPlanDAOTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllWorkPlansSuccessfulTest(){

        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        List<WorkPlan> workPlansExpectedList = new ArrayList<>();
        Date startDate =  new Date(2022-1900,12-1,4);
        Date endDate =  new Date(2023-1900,12-1,4);
        WorkPlan workPlanExpected = new WorkPlan("P_LIS_UVitsCA", startDate, endDate,"AGP-1", "SASO750909HDFNNS05");
        workPlansExpectedList.add(workPlanExpected);
        try {
            List<WorkPlan> workPlansResultList = workPlanDAO.displayAllWorkPlans();
            assertEquals("Prueba obtener todos los planes de trabajo existentes", workPlansExpectedList, workPlansResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllWorkPlansFailedTest(){

        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        List<WorkPlan> workPlansExpectedList = new ArrayList<>();
        try {
            List<WorkPlan> workPlansResultList = workPlanDAO.displayAllWorkPlans();
            assertEquals("Prueba alterna con lista vacia de planes de trabajo", workPlansExpectedList, workPlansResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addWorkPlanSuccessfulTest(){

        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        Date startDate =  new Date(2022-1900,12-1,4);
        Date endDate =  new Date(2023-1900,12-1,4);
        WorkPlan workPlanExpected = new WorkPlan("P_LIS_UVitsCA", startDate, endDate,"AGP-1", "SASO750909HDFNNS05");
        try {
            boolean workPlansAddedResult = workPlanDAO.addWorkPlan(workPlanExpected);
            assertTrue("Prueba insertar un plan de trabajo correctamente", workPlansAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateWorkPlanSuccessfulTest(){

        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        String originalKeyWorkPlan = "P_LIS_UVitsCA";
        String newKeyWorkPlan = "P_LIS_UVitsCAA";
        try {
            WorkPlan workPlan = workPlanDAO.foundWorkPlanByKeyWorkPlan(originalKeyWorkPlan);
            Date newStartDate = new Date(2023 - 1900, 11 - 1, 3);
            Date newEndDate = new Date(2024 - 1900, 11 - 1, 3);

            workPlan.setKeyWorkPlan(newKeyWorkPlan);
            workPlan.setStartDate(newStartDate);
            workPlan.setEndDate(newEndDate);
            workPlan.setKeyAcademicGroup("AGP-2");
            workPlan.setCurpMember("SASO750909HDFNNS05");

            boolean updatedWorkPlansResult = workPlanDAO.updateWorkPlan(workPlan, originalKeyWorkPlan);
            assertTrue("Prueba actualizar un plan de trabajo correctamente", updatedWorkPlansResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deleteWorkPlanSuccessfulTest(){

        String keyWorkPlan = "P_LIS_UVitsCA";
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            boolean workPlansDeletedResult = workPlanDAO.deleteWorkPlan(keyWorkPlan);
            assertTrue("Prueba eliminar un plan de trabajo correctamente", workPlansDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteWorkPlanFailedTest(){

        String keyWorkPlan = "WOP-100";
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            boolean workPlansDeletedResult = workPlanDAO.deleteWorkPlan(keyWorkPlan);
            assertFalse("Prueba eliminar un plan de trabajo  inexistente", workPlansDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundWorkPlanByKeyWorkPlanSuccessfulTest(){

        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        String keyWorkPlan = "P_LIS_UVitsCA";
        Date startDate =  new Date(2022-1900,12-1,4);
        Date endDate =  new Date(2023-1900,12-1,4);
        WorkPlan workPlanExpected = new WorkPlan(keyWorkPlan, startDate, endDate,"AGP-1", "SASO750909HDFNNS05");
        try {
            WorkPlan workPlanResult = workPlanDAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);
            assertEquals("Prueba verificar que un plan de trabajo exista", workPlanExpected, workPlanResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundWorkPlanByKeyWorkPlanFailedTest(){
        String keyWorkPlan = "WOP-100";
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            assertNull("Prueba alterna, no existe el plan de trabajo", workPlanDAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan));
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

}
