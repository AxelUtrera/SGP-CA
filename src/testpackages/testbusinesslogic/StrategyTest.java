package testpackages.testbusinesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import businesslogic.StrategyDAO;
import domain.Strategy;
import org.junit.Test;

public class StrategyTest {

    @Test
    public void displayAllStrategiesSuccessfulTest() throws SQLException {

        StrategyDAO strategyDAO = new StrategyDAO();
        List<Strategy> strategiesExpected = new ArrayList<>();
        Strategy strategyExpected1 = new Strategy("fff556","implementacion", "x", "x", 2, "y");
        Strategy strategyExpected2 = new Strategy("ue3j4","aplicacion", "implementacion", "Preparar", 1, "Mejorar");
        strategiesExpected.add(strategyExpected1);
        strategiesExpected.add(strategyExpected2);
        List<Strategy> strategiesResult = strategyDAO.displayAllStrategies();

        assertEquals("Prueba obtener todas las estrategias existentes", strategiesExpected, strategiesResult);
    }

    @Test
    public void displayAllStrategiesFailedTest() throws SQLException {

        StrategyDAO strategyDAO = new StrategyDAO();

        List<Strategy> strategiesExpected = new ArrayList<>();
        List<Strategy> strategiesResult = strategyDAO.displayAllStrategies();

        assertEquals("Prueba alterna con lista vacia de estrategias", strategiesExpected, strategiesResult);
    }

    @Test
    public void addStrategySuccessfulTest() throws SQLException {

        Strategy strategyExpected = new Strategy("ii4d", "implementacion", "implementacion", "Preparar", 3, "Mejorar");
        StrategyDAO strategyDAO = new StrategyDAO();
        strategyDAO.addStrategy(strategyExpected);
        Strategy strategyResult = strategyDAO.foundStrategyById("ii4d");
        assertEquals("Prueba insertar una estrategia correctamente", strategyExpected, strategyResult);
    }

//    @Test
//    public void deleteOneStrategySuccessfulTest() throws SQLException {
//
//        String idStrategy = "ii4d";
//        Strategy strategy = new Strategy(idStrategy, "implementacion", "implementacion", "Preparar", 3, "Mejorar");
//        StrategyDAO strategyDAO = new StrategyDAO();
//        assertNotNull("Prueba encontrar una estrategia antes de ser borrada", strategyDAO.foundStrategyById(idStrategy));
//        strategyDAO.deleteOneStrategy(strategy);
//        assertNull("Prueba eliminar una estrategia correctamente", strategyDAO.foundStrategyById(idStrategy));
//
//    }

//    @Test
//    public void deleteOneStrategyFailedTest() throws SQLException {
//
//        String idStrategy = "ii4dD";
//        Strategy strategy = new Strategy(idStrategy, "implementacion", "implementacion", "Preparar", 3, "Mejorar");
//        StrategyDAO strategyDAO = new StrategyDAO();
//        assertNull("Prueba alterna de no encontrar estrategia antes de ser borrada", strategyDAO.foundStrategyById(idStrategy));
//        strategyDAO.deleteOneStrategy(strategy);
//        assertNull("Prueba eliminar una estrategia correctamente", strategyDAO.foundStrategyById(idStrategy));
//
//    }

    @Test
    public void foundStrategyByIdSuccessfulTest() throws SQLException {
        String idStrategy = "ii4d";
        StrategyDAO strategyDAO = new StrategyDAO();
        assertNotNull("Prueba verificar que una estrategia exista", strategyDAO.foundStrategyById(idStrategy));
    }

    @Test
    public void foundStrategyByIdFailedTest() throws SQLException {
        String idStrategy = "ii4dD";
        StrategyDAO strategyDAO = new StrategyDAO();
        assertNull("Prueba alterna, no existe la estrategia", strategyDAO.foundStrategyById(idStrategy));

    }

}
