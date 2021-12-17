package testpackages.testdataaccess;

import dataaccess.Propertie;
import dataaccess.PropertieDAO;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class PropertieTest {

    @Test
    public void getPropertiesTest() throws SQLException {

        PropertieDAO propertieDAO = new PropertieDAO();
        Propertie propertieExpected = new Propertie("jdbc:mysql://localhost:3306/sgp-ca?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true", "Rodri", "admin");
        Propertie propertieResult = propertieDAO.getProperties();
        assertEquals("Prueba obtener las propiedades para conectar a la base de datos", propertieExpected, propertieResult);
    }
}
