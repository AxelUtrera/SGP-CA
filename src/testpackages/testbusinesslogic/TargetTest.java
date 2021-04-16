package testpackages.testbusinesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import businesslogic.TargetDAO;
import domain.Strategy;
import org.junit.Test;

public class TargetTest {

    @Test
    public void testDisplayAllTargetStrategies() throws SQLException {

        TargetDAO targetDAO = new TargetDAO();
        List<Strategy> targetStrategiesExpected = new ArrayList<>();
        Strategy targetStrategyExpected1 = new Strategy("fff556","implementacion", "x", "x", 2, "y");
        Strategy targetStrategyExpected2 = new Strategy("ue3j4","aplicacion", "implementacion", "Preparar", 1, "Mejorar");
        targetStrategiesExpected.add(targetStrategyExpected1);
        targetStrategiesExpected.add(targetStrategyExpected2);
        List<Strategy> strategiesResult = targetDAO.displayAllTargetStrategies("ffg5");
        assertEquals("Prueba obtener todas las estrategias asociadas a un objetivo", targetStrategiesExpected, strategiesResult);

    }

}
