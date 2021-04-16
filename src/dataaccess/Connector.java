package dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {

    public Connection getConnection() throws SQLException {

        Connection connection = null;
        Propertie propertie = getProperties();
        connection = DriverManager.getConnection(propertie.getURL(), propertie.getUSER(), propertie.getPASSWORD());

        return connection;
    }

    public Propertie getProperties(){

        Propertie propertie = null;

        try{

            InputStream input = new FileInputStream("src\\dataaccess\\data.properties");
            Properties properties = new Properties();
            properties.load(input);
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            input.close();

            propertie = new Propertie(URL, USER, PASSWORD);

        }catch(FileNotFoundException ex){
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return propertie;

    }

    public void close(Connection connection) throws SQLException{
        if(connection!=null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
