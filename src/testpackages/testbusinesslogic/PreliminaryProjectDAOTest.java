package testpackages.testbusinesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import businesslogic.BusinessLogicException;
import businesslogic.PreliminaryProjectDAO;
import controller.AlertInterface;
import domain.PreliminaryProject;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PreliminaryProjectDAOTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllPreliminaryProjectsSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        List<PreliminaryProject> preliminaryProjectsExpectedList = new ArrayList<>();
        Date starDate =  new Date(2020-1900,12-1,22);
        PreliminaryProject preliminaryProjectExpected = new PreliminaryProject("PLP-1", "Teoría de los microservicios",
                "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios", starDate,
                "Asignada", "Tesina", 24, "IVP-1");
        preliminaryProjectsExpectedList.add(preliminaryProjectExpected);
        try {
            List<PreliminaryProject> preliminaryProjectsResultList = preliminaryProjectDAO.displayAllPreliminaryProjects();
            assertEquals("Prueba obtener todos los anteproyectos existentes", preliminaryProjectsExpectedList, preliminaryProjectsResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllWorkPlansFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        List<PreliminaryProject> preliminaryProjectsExpectedList = new ArrayList<>();
        try {
            List<PreliminaryProject> preliminaryProjectsResultList = preliminaryProjectDAO.displayAllPreliminaryProjects();
            assertEquals("Prueba alterna con lista vacia de anteproyectos", preliminaryProjectsExpectedList, preliminaryProjectsResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProject = new PreliminaryProjectDAO();
        Date starDate =  new Date(2020-1900,12-1,22);
        PreliminaryProject preliminaryProjectExpected = new PreliminaryProject( "Teoría de los microservicios",
                "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios", starDate,
                "Asignada", "Tesina", 24, "IVP-1");
        try {
            boolean preliminaryProjectsAddedResult = preliminaryProject.addPreliminaryProject(preliminaryProjectExpected);
            assertTrue("Prueba insertar un anteproyecto correctamente", preliminaryProjectsAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdPreliminaryProjectNumberSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProject = new PreliminaryProjectDAO();
        int idPreliminaryProjectExpected = 1;
        try {
            int idPreliminaryProjectResult = preliminaryProject.getLastIdNumber("idPreliminaryProject", "preliminaryproject");
            assertEquals("Prueba obtener el numero entero del ultimo idPreliminaryProject",
                    idPreliminaryProjectExpected, idPreliminaryProjectResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdPreliminaryProjectStudentNameSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProject = new PreliminaryProjectDAO();
        int idPreliminaryProjectStudentNameExpected = 1;
        try {
            int idPreliminaryProjectStudentNameResult = preliminaryProject.getLastIdNumber("idPreliminaryProjectStudentInformation",
                    "`preliminaryproject-studentinformation`");
            assertEquals("Prueba obtener el numero entero del ultimo idPreliminaryProjectStudentInformation",
                    idPreliminaryProjectStudentNameExpected, idPreliminaryProjectStudentNameResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updatePreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProject = "PLP-1";
        try {
            PreliminaryProject preliminaryProject = preliminaryProjectDAO.foundPreliminaryProjectById(idPreliminaryProject);
            Date newStartDate = new Date(2023 - 1900, 11 - 1, 3);

            preliminaryProject.setTitle("Arquitecturas y Líneas de Productos de Software");
            preliminaryProject.setDescription("Este proyecto de investigación se basa en los resultados obtenidos de la" +
                    " investigación doctoral titulada AOPLA, la cual se ubica en el área de Ingeniería de Líneas de Productos de Software");
            preliminaryProject.setStartDate(newStartDate);
            preliminaryProject.setStatus("Asignada");
            preliminaryProject.setModality("Práctico-Técnico");
            preliminaryProject.setDuration(12);
            preliminaryProject.setIdInvestigationProject("IVP-1");

            boolean updatedpreliminaryProjectsResult = preliminaryProjectDAO.updatePreliminaryProject(preliminaryProject);
            assertTrue("Prueba actualizar un anteproyecto correctamente", updatedpreliminaryProjectsResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deletePreliminaryProjectSuccessfulTest(){

        String idPreliminaryProject = "PLP-1";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectsDeletedResult = preliminaryProjectDAO.deletePreliminaryProject(idPreliminaryProject);
            assertTrue("Prueba eliminar un anteproyecto correctamente", preliminaryProjectsDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deletePreliminaryProjectFailedTest(){

        String idPreliminaryProject = "PLP-100";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectsDeletedResult = preliminaryProjectDAO.deletePreliminaryProject(idPreliminaryProject);
            assertFalse("Prueba eliminar un anteproyecto inexistente", preliminaryProjectsDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundPreliminaryProjectByIdSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProject = "PLP-1";
        Date starDate =  new Date(2020-1900,12-1,22);
        PreliminaryProject preliminaryProjectExpected = new PreliminaryProject(idPreliminaryProject, "Teoría de los microservicios",
                "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios", starDate,
                "Asignada", "Tesina", 24, "IVP-1");
        try {
            PreliminaryProject preliminaryProyectResult = preliminaryProjectDAO.foundPreliminaryProjectById(idPreliminaryProject);
            assertEquals("Prueba encontrar un anteproyecto por su id", preliminaryProjectExpected, preliminaryProyectResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundPreliminaryProjectByIdFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProject = "PLP-100";
        try {
            assertNull("Prueba alterna, no existe el anteproyecto", preliminaryProjectDAO.foundPreliminaryProjectById(idPreliminaryProject));
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundPreliminaryProjectsByIdInvestigationProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idInvestigationProject = "IVP-1";
        List<PreliminaryProject> preliminaryProjectsExpectedList = new ArrayList<>();
        Date starDate =  new Date(2020-1900,12-1,22);
        PreliminaryProject preliminaryProjectExpected = new PreliminaryProject("PLP-1", "Teoría de los microservicios",
                "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios", starDate,
                "Asignada", "Tesina", 24, idInvestigationProject);
        preliminaryProjectsExpectedList.add(preliminaryProjectExpected);
        try {
            List<PreliminaryProject> preliminaryProjectsResultList =
                    preliminaryProjectDAO.foundPreliminaryProjectsByIdInvestigationProject(idInvestigationProject);

            assertEquals("Prueba encontrar una lista de anteproyectos asociados a un idInvestigationProject",
                    preliminaryProjectsExpectedList, preliminaryProjectsResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundPreliminaryProjectsByIdInvestigationProjectFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idInvestigationProject = "IVP-100";
        List<PreliminaryProject> preliminaryProjectsExpectedList = new ArrayList<>();
        try {
            List<PreliminaryProject> preliminaryProjectsResultList =
                    preliminaryProjectDAO.foundPreliminaryProjectsByIdInvestigationProject(idInvestigationProject);
            assertEquals("Prueba encontrar una lista vacia de anteproyectos asociados a un idInvestigationProject inexistente",
                    preliminaryProjectsExpectedList, preliminaryProjectsResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addStudentInformationToPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectsAddedResult = preliminaryProjectDAO.addStudentInformationToPreliminaryProject("PLP-1",
                    "Calos Edson Romero [carlos@gmail.com]");
            assertTrue("Prueba agregar la información de un estudiante a un anteproyecto correctamente", preliminaryProjectsAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundStudentInformationByIdPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-1";
        List<String> preliminaryProjectStudentInformationExpectedList = new ArrayList<>();
        preliminaryProjectStudentInformationExpectedList.add("Calos Edson Romero [carlos@gmail.com]");
        try {
            List<String> preliminaryProjectStudentInformationResultList =
                    preliminaryProjectDAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista con la información de los estudiantes asociados a un anteproyecto",
                    preliminaryProjectStudentInformationExpectedList, preliminaryProjectStudentInformationResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundStudentInformationByIdPreliminaryProjectFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-100";
        List<String> preliminaryProjectStudentNamesExpectedList = new ArrayList<>();
        try {
            List<String> preliminaryProjectStudentNamesResultList =
                    preliminaryProjectDAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista vacia de la información de los estudiantes asociados a un anteproyecto inexistente",
                    preliminaryProjectStudentNamesExpectedList, preliminaryProjectStudentNamesResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectStudentSuccessfulTest(){

        String idPreliminaryProject = "PLP-1";
        String studentInformation = "Calos Edson Romero [carlos@gmail.com]";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectStudentDeletedResult = preliminaryProjectDAO.deletePreliminaryProjectStudent(idPreliminaryProject, studentInformation);
            assertTrue("Prueba eliminar un la información de un estudiante de un anteproyecto correctamente", preliminaryProjectStudentDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectStudentFailedTest(){

        String idPreliminaryProject = "PLP-100";
        String studentInformation = "Calos Edson Romero [carlos@gmail.com]";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectStudentDeletedResult = preliminaryProjectDAO.deletePreliminaryProjectStudent(idPreliminaryProject, studentInformation);
            assertFalse("Prueba eliminar la información de un estudiante de un anteproyecto inexistente", preliminaryProjectStudentDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addLgacToPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectsAddedResult = preliminaryProjectDAO.addLgacToPreliminaryProject("PLP-1", "LGAC1");
            assertTrue("Prueba agregar una LGAC a un anteproyecto correctamente", preliminaryProjectsAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundLgacByIdPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-1";
        List<String> preliminaryProjectLGACExpectedList = new ArrayList<>();
        preliminaryProjectLGACExpectedList.add("LGAC1");
        try {
            List<String> preliminaryProjectLGACResultList = preliminaryProjectDAO.foundLgacByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista de todas las LGAC asociados a un anteproyecto",
                    preliminaryProjectLGACExpectedList, preliminaryProjectLGACResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundLgacByIdPreliminaryProjectFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-100";
        List<String> preliminaryProjectLGACExpectedList = new ArrayList<>();
        try {
            List<String> preliminaryProjectLGACResultList = preliminaryProjectDAO.foundLgacByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista vacia de LGAC asociados a un anteproyecto inexistente",
                    preliminaryProjectLGACExpectedList, preliminaryProjectLGACResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectLgacSuccessfulTest(){

        String idPreliminaryProyect = "PLP-1";
        String lgac = "LGAC1";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectLGACDeletedResult = preliminaryProjectDAO.deletePreliminaryProjectLgac(idPreliminaryProyect, lgac);
            assertTrue("Prueba eliminar una LGAC de un anteproyecto correctamente", preliminaryProjectLGACDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectLgacFailedTest(){

        String idPreliminaryProyect = "PLP-100";
        String lgac = "LGAC1";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectLGACDeletedResult = preliminaryProjectDAO.deletePreliminaryProjectLgac(idPreliminaryProyect, lgac);
            assertFalse("Prueba eliminar una LGAC de un anteproyecto inexistente", preliminaryProjectLGACDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addCodirectorToPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectCodirectrorAddedResult = preliminaryProjectDAO.addCodirectorToPreliminaryProject("PLP-1",
                    "Calos Edson Romero [carlos@gmail.com]");
            assertTrue("Prueba agregar la información de un codirector a un anteproyecto correctamente", preliminaryProjectCodirectrorAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundCodirectorsByIdPreliminaryProjectSuccessfulTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-1";
        List<String> preliminaryProjectCodirectorsInformationExpectedList = new ArrayList<>();
        preliminaryProjectCodirectorsInformationExpectedList.add("Calos Edson Romero [carlos@gmail.com]");
        try {
            List<String> preliminaryProjectCodirectorsInformationResultList =
                    preliminaryProjectDAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista con la información de los codirectores asociados a un anteproyecto",
                    preliminaryProjectCodirectorsInformationExpectedList, preliminaryProjectCodirectorsInformationResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundCodirectorsByIdPreliminaryProjectFailedTest(){

        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String idPreliminaryProyect = "PLP-100";
        List<String> preliminaryProjectCodirectorsInformationExpectedList = new ArrayList<>();
        try {
            List<String> preliminaryProjectCodirectorsInformationResultList =
                    preliminaryProjectDAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProyect);
            assertEquals("Prueba encontrar una lista vacia de la información de los codirectores asociados a un anteproyecto inexistente",
                    preliminaryProjectCodirectorsInformationExpectedList, preliminaryProjectCodirectorsInformationResultList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectCodirectorSuccessfulTest(){

        String idPreliminaryProject = "PLP-1";
        String codirectorInformation = "Calos Edson Romero [carlos@gmail.com]";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectCodirectorDeletedResult =
                    preliminaryProjectDAO.deletePreliminaryProjectCodirector(idPreliminaryProject, codirectorInformation);
            assertTrue("Prueba eliminar un la información de un codirector de un anteproyecto correctamente",
                    preliminaryProjectCodirectorDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deletePreliminaryProjectCodirectorFailedTest(){

        String idPreliminaryProject = "PLP-100";
        String codirectorInformation = "Calos Edson Romero [carlos@gmail.com]";
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        try {
            boolean preliminaryProjectCodirectorDeletedResult =
                    preliminaryProjectDAO.deletePreliminaryProjectCodirector(idPreliminaryProject, codirectorInformation);
            assertFalse("Prueba eliminar la información de un codirectore de un anteproyecto inexistente",
                    preliminaryProjectCodirectorDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

}
