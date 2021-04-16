package testpackages.testdataaccess;

import java.sql.Connection;
import java.sql.SQLException;

import dataaccess.Connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import dataaccess.Propertie;
import org.junit.Test;

public class DataAccessTest {

    @Test
    public void getConnectionTest() throws SQLException{

        Connector connector = new Connector();
        Connection currentConnection = connector.getConnection();
        assertNotNull(currentConnection);

    }

    @Test
    public void getPropertiesTest() throws SQLException{

        Propertie propertieExpected = new Propertie("jdbc:mysql://localhost:3306/sgp-caa?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true", "Rodri", "admin");
        Connector connector = new Connector();
        Propertie propertieResult = connector.getProperties();
        assertEquals("Prueba obtener las propiedades para conectar a la base de datos", propertieExpected, propertieResult);
    }


}
