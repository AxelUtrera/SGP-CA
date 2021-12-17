package testpackages.testdataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import dataaccess.Connector;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

public class ConnectorTest {

    @Test
    public void getConnectionTest() throws SQLException{

        Connector connector = new Connector();
        Connection currentConnection = connector.getConnection();
        assertNotNull(currentConnection);

    }

    @Test
    public void closeConnectionTest() throws  SQLException{
        Connector connector = new Connector();
        Connection connection = connector.getConnection();
        assertNotSame(connection, connector.closeConnection(connection));
    }

}
