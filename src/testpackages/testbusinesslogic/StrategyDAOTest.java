package testpackages.testbusinesslogic;

import java.util.ArrayList;
import java.util.List;

import businesslogic.BusinessLogicException;
import businesslogic.StrategyDAO;
import controller.AlertInterface;
import domain.Strategy;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class StrategyDAOTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllStrategiesSuccessfulTest(){

        StrategyDAO strategyDAO = new StrategyDAO();
        List<Strategy> strategiesExpected = new ArrayList<>();
        Strategy strategyExpected = new Strategy("STR-1","Desarrollo de filtro para docencia de tiempo completo",
                "Conocer los programas de intercambio docente con otras universidades o facultades de área técnica",
                "Obtener la acreditación de un nuevo integrante del CA mediante su alta en PRODEP y en el sistema PLATA UV",
                1, "Incorporación de 1 integrante para septiembre del 2020", "TRG-1");
        strategiesExpected.add(strategyExpected);
        try {
            List<Strategy> strategiesResult = strategyDAO.displayAllStrategies();
            assertEquals("Prueba obtener todas las estrategias existentes", strategiesExpected, strategiesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllStrategiesFailedTest(){

        StrategyDAO strategyDAO = new StrategyDAO();

        List<Strategy> strategiesExpected = new ArrayList<>();
        try {
            List<Strategy> strategiesResult = strategyDAO.displayAllStrategies();
            assertEquals("Prueba alterna con lista vacia de estrategias", strategiesExpected, strategiesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addStrategySuccessfulTest(){

        StrategyDAO strategyDAO = new StrategyDAO();
        Strategy strategyExpected = new Strategy("Desarrollo de filtro para docencia de tiempo completo",
                "Conocer los programas de intercambio docente con otras universidades o facultades de área técnica",
                "Obtener la acreditación de un nuevo integrante del CA mediante su alta en PRODEP y en el sistema PLATA UV",
                1, "Incorporación de 1 integrante para septiembre del 2020", "TRG-1");
        try {
            boolean strategiesAddedResult = strategyDAO.addStrategy(strategyExpected);
            assertTrue("Prueba insertar una estrategia correctamente", strategiesAddedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void getLastIdStrategyNumberSuccessfulTest(){

        StrategyDAO strategyDAO = new StrategyDAO();
        int idStrategyExpected = 2;
        try {
            int idStrategyResult = strategyDAO.getLastIdStrategyNumber();
            assertEquals("Prueba obtener el numero entero de la ultima idStrategy", idStrategyExpected, idStrategyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void deleteStrategySuccessfulTest(){

        String idStrategy = "STR-1";
        StrategyDAO strategyDAO = new StrategyDAO();
        try {
            boolean strategiesDeletedResult = strategyDAO.deleteStrategy(idStrategy);
            assertTrue("Prueba eliminar una estrategia correctamente", strategiesDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteStrategyFailedTest(){

        String idStrategy = "SRT-1000";
        StrategyDAO strategyDAO = new StrategyDAO();
        try {
            boolean strategiesDeletedResult = strategyDAO.deleteStrategy(idStrategy);
            assertFalse("Prueba eliminar una estrategia inexistente", strategiesDeletedResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundStrategyByIdStrategySuccessfulTest(){
        String idStrategy = "STR-1";
        StrategyDAO strategyDAO = new StrategyDAO();
        Strategy strategyExpected = new Strategy(idStrategy,"Desarrollo de filtro para docencia de tiempo completo",
                "Conocer los programas de intercambio docente con otras universidades o facultades de área técnica",
                "Obtener la acreditación de un nuevo integrante del CA mediante su alta en PRODEP y en el sistema PLATA UV",
                1, "Incorporación de 1 integrante para septiembre del 2020", "TRG-1");
        try {
            Strategy strategyResult = strategyDAO.foundStrategyByIdStrategy(idStrategy);
            assertEquals("Prueba verificar que una estrategia exista", strategyExpected, strategyResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundStrategyByIdStrategyFailedTest(){
        String idStrategy = "STR-100";
        StrategyDAO strategyDAO = new StrategyDAO();
        try {
            assertNull("Prueba alterna, no existe la estrategia", strategyDAO.foundStrategyByIdStrategy(idStrategy));
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundStrategiesByIdTargetSuccessfulTest(){

        StrategyDAO strategyDAO = new StrategyDAO();
        String idTarget = "TRG-1";
        List<Strategy> strategiesExpected = new ArrayList<>();
        Strategy strategyExpected = new Strategy("STR-1","Desarrollo de filtro para docencia de tiempo completo",
                "Conocer los programas de intercambio docente con otras universidades o facultades de área técnica",
                "Obtener la acreditación de un nuevo integrante del CA mediante su alta en PRODEP y en el sistema PLATA UV",
                1, "Incorporación de 1 integrante para septiembre del 2020", "TRG-1");
        strategiesExpected.add(strategyExpected);
        try {
            List<Strategy> strategiesResult = strategyDAO.foundStrategiesByIdTarget(idTarget);
            assertEquals("Prueba obtener todas las estrategias asociadas a un idTarget", strategiesExpected, strategiesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundStrategiesByidTargetFailedTest(){

        StrategyDAO strategyDAO = new StrategyDAO();

        String idTarget="TRG-100";
        List<Strategy> strategiesExpected = new ArrayList<>();
        try {
            List<Strategy> strategiesResult = strategyDAO.foundStrategiesByIdTarget(idTarget);
            assertEquals("Prueba alterna con lista vacia de estrategias asociadas a un idTarget inexistente", strategiesExpected, strategiesResult);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

}
