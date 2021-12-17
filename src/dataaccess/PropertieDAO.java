package dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertieDAO {

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
}
