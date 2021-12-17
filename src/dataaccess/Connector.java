package dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {

    public Connection getConnection() throws SQLException {

        Connection connection = null;
        PropertieDAO propertieDAO = new PropertieDAO();
        Propertie propertie = propertieDAO.getProperties();
        connection = DriverManager.getConnection(propertie.getURL(), propertie.getUSER(), propertie.getPASSWORD());

        return connection;
    }


    public Connection closeConnection(Connection connection) throws SQLException{
        if(connection!=null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                    connection = null;
                }
            }catch(SQLException ex){
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return connection;
    }

}
