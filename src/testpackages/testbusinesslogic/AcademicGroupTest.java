package testpackages.testbusinesslogic;

import businesslogic.AcademicGroupDAO;
import businesslogic.BusinessLogicException;
import controller.AlertInterface;
import domain.AcademicGroup;
import org.junit.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AcademicGroupTest {

    private Date date =  new Date(2001,01,31);
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllAcademicGroupsSuccessfulTest(){ //FAIL

        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        List<AcademicGroup> academicGroupsExpected = new ArrayList<>();
        AcademicGroup academicGroupExpected1 = new AcademicGroup("ACG-1", "Informatica", "Dr. Karen", "en consolidacion");
        academicGroupsExpected.add(academicGroupExpected1);
        try{
            List<AcademicGroup> academicGroupsResult = academicGroupDAO.displayAllAcademicGroups();
            assertEquals("Prueba obtener todas los cuerpos academicos existentes", academicGroupsExpected, academicGroupsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllAcademicGroupsFailedTest(){

        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();

        List<AcademicGroup> academicGroupsExpected = new ArrayList<>();
        try{
            List<AcademicGroup> academicGroupsResult = academicGroupDAO.displayAllAcademicGroups();
            assertEquals("Prueba alterna con lista vacia de cuerpos academicos", academicGroupsExpected, academicGroupsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addAcademicGroupsuccessfulTest(){

        AcademicGroup academicGroupExpected = new AcademicGroup("ACP-1", "Estadistica", "Dr. Angel", "en consolidacion");
        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        try {

            boolean academicResult= academicGroupDAO.addAcademicGroup(academicGroupExpected);
            assertTrue(academicResult);

        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



    @Test
    public void updateAcademicGroupTest(){

        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        String key = "ACG-13SS";
        try{
            AcademicGroup academicGroup = academicGroupDAO.foundAcademicGroupByID(key);
            academicGroup.setDegreeConsolidation("Consolidado");
            boolean updatedAcademicGroupsResult = academicGroupDAO.updateAcademicGroup(academicGroup);
            assertTrue(updatedAcademicGroupsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteAcademicGroupSuccessfulTest(){

        String key = "ACG-13";
        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        try{
            boolean academicGroupsDeletedResult = academicGroupDAO.deleteAcademicGroup(key);
            assertTrue(academicGroupsDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteAcademicGroupFailedTest(){

        String key = "ACG-30";
        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        try{
            boolean academicGroupsDeletedResult = academicGroupDAO.deleteAcademicGroup(key);
            assertTrue(academicGroupsDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundAcademicGroupByKeySuccessfulTest(){
        String key = "ACG-13";
        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        AcademicGroup academicGroupExpected = new AcademicGroup("ACG-13", "Informatica", "Dr. Karen", "en consolidacion");
        try{
            AcademicGroup academicGroupResult = academicGroupDAO.foundAcademicGroupByID(key);
            assertEquals("Prueba verificar que un cuerpo academico exista", academicGroupExpected.getKey(), academicGroupResult.getKey());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundAcademicGroupByKeyFailedTest(){
        String key = "ACG-30";
        AcademicGroupDAO academicGroupDAO = new AcademicGroupDAO();
        try{
            assertNull("Prueba alterna, no existe el cuerpo academico", academicGroupDAO.foundAcademicGroupByID(key));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
