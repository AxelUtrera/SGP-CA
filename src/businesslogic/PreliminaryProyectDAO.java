package businesslogic;

import dataaccess.Connector;

import domain.PreliminaryProyect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PreliminaryProyectDAO implements IPreliminaryProyectDAO {

    private Connector connector = new Connector();

    public List<PreliminaryProyect> displayAllPreliminaryProyects() {

        String SQL_SELECT = "SELECT idPreliminaryProyect, title, description, startDate, status, modality, duration, lgac FROM preliminaryproyect";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreliminaryProyect preliminaryProyect = null;
        List<PreliminaryProyect> preliminaryProyectList = new ArrayList<>();

        try {

            connection = connector.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String idPreliminaryProyect = resultSet.getString("idPreliminaryProyect");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String startDate = resultSet.getString("startDate");
                String status = resultSet.getString("status");
                String modality = resultSet.getString("modality");
                String duration = resultSet.getString("duration");
                String lgac = resultSet.getString("lgac");

                preliminaryProyect = new PreliminaryProyect(idPreliminaryProyect, title, description, startDate, status, modality, duration, lgac);
                preliminaryProyectList.add(preliminaryProyect);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return preliminaryProyectList;
    }


}
