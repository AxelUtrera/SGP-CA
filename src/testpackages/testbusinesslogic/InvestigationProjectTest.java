package testpackages.testbusinesslogic;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import controller.AlertInterface;
import domain.InvestigationProject;
import org.junit.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class InvestigationProjectTest {

    private Date date =  new Date(2001,01,31);
    private Date date2 =  new Date(2021,03,21);
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllInvestigationProyectsSuccessfulTest(){ //FAIL

        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        List<InvestigationProject> investigationProyectsExpected = new ArrayList<>();
        InvestigationProject investigationProjectExpected1 = new InvestigationProject("IVP-1", "Diseño en construccion", date, date2, "Descripcion");
        InvestigationProject investigationProjectExpected2 = new InvestigationProject("IVP-2", "Complejidad Ciclomatica", date, date2, "Descripcion");
        investigationProyectsExpected.add(investigationProjectExpected1);
        investigationProyectsExpected.add(investigationProjectExpected2);
        try{
            List<InvestigationProject> investigationProyectsResult = investigationProjectDAO.displayAllinvestigationProjects();

            assertEquals("Prueba obtener todas los proyectos de investigacion existentes", investigationProyectsExpected, investigationProyectsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllInvestigationProyectsFailedTest(){

        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            List<InvestigationProject> investigationProyectsExpected = new ArrayList<>();
            List<InvestigationProject> investigationProyectsResult = investigationProjectDAO.displayAllinvestigationProjects();

            assertEquals("Prueba alterna con lista vacia de proyectos de investigacion", investigationProyectsExpected, investigationProyectsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addInvestigationProyectSuccessfulTest(){

        InvestigationProject investigationProjectExpected = new InvestigationProject("Diseño en construccion", date, date2, "Descripcion");
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            boolean InvestigationProyectsAddedResult = investigationProjectDAO.addInvestigationProject(investigationProjectExpected);
            assertTrue(InvestigationProyectsAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }
    @Test
    public void addInvestigationProyectSuccessfulTest2(){

        InvestigationProject investigationProjectExpected = new InvestigationProject("Complejidad Ciclomatica", date, date2, "Descripcion");;
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            boolean InvestigationProyectsAddedResult = investigationProjectDAO.addInvestigationProject(investigationProjectExpected);
            assertTrue(InvestigationProyectsAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateInvestigationProyectTest(){

        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        String idInvestigationProyect = "IVP-1";
        try{
            InvestigationProject investigationProject = investigationProjectDAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProyect);
            investigationProject.setTitle("Diagramas de robustez");
            investigationProject.setDescription("Descripcion larga");
            boolean updatedInvestigationProyectsResult = investigationProjectDAO.updateInvestigationProject(investigationProject);
            assertTrue(updatedInvestigationProyectsResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteInvestigationProyectSuccessfulTest(){

        String idInvestigationProyect = "IVP-2";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            boolean InvestigationProyectsDeletedResult = investigationProjectDAO.deleteInvestigationProject(idInvestigationProyect);
            assertTrue(InvestigationProyectsDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteInvestigationProyectFailedTest(){

        String idInvestigationProyect = "IVP-040";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            boolean InvestigationProyectsDeletedResult = investigationProjectDAO.deleteInvestigationProject(idInvestigationProyect);
            assertTrue(InvestigationProyectsDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundInvestigationProyectByIDSuccessfulTest(){
        String idInvestigationProyect = "IVP-1";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject memberExpected = new InvestigationProject("IVP-1", "Diseño en construccion", date, date2, "Descripcion");
        try{
            InvestigationProject memberResult = investigationProjectDAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProyect);
            assertEquals("Prueba verificar que un proyecto exista", memberExpected.getIdInvestigationProject(), memberResult.getIdInvestigationProject());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundInvestigationProyectByIDFailedTest(){
        String idInvestigationProyect = "IVP-065";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            assertNull("Prueba alterna, no existe el proyecto", investigationProjectDAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProyect));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }


    @Test
    public void foundInvestigationProyectByTitleSuccessfulTest(){
        String title = "Diagramas de robustez";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject memberExpected = new InvestigationProject("IVP-1", "Diagramas de robustez", date, date2, "Descripcion");
        try{
            InvestigationProject memberResult = investigationProjectDAO.foundInvestigationProjectByTitle(title);
            assertEquals("Prueba verificar que un proyecto exista", memberExpected.getTitle(), memberResult.getTitle());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundInvestigationProyectByTitleFailedTest(){
        String title = "Casos de uso";
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try{
            assertNull("Prueba alterna, no existe el proyecto", investigationProjectDAO.foundInvestigationProjectByTitle(title));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
