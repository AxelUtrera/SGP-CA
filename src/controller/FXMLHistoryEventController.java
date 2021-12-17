package controller;

import businesslogic.BusinessLogicException;
import businesslogic.EventDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import domain.Event;
import domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLHistoryEventController implements Initializable{

    private final EventDAO EVENT_DAO = new EventDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private ObservableList<Event> eventObservableList;
    private Event eventSelected=null;
    private double newTargetPaneX;
    private double newTargetPaneY;
    private static String memberInformation;

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneConsultEvent;
    @FXML
    private TableView<Event> tableViewEvents;
    @FXML
    private TableColumn<Event, String> tableColumnType;
    @FXML
    private TableColumn<Event, String> tableColumnTitle;
    @FXML
    private TableColumn<Event, Date> tableColumnDate;
    @FXML
    private JFXTextArea textAreaTitleEvent;
    @FXML
    private JFXTextArea textAreaDateEvent;
    @FXML
    private JFXTextArea textAreaPlaceEvent;
    @FXML
    private JFXTextArea textAreaTypeEvent;
    @FXML
    private JFXButton buttonEventConstancies;
    @FXML
    private Label labelMemberInformation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeEventsTable();
        setAcademicEvents();
        clickedEvent();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
        }

    }

    public void setMemberInformation(String memberLoginInformation){

        memberInformation = memberLoginInformation;
        labelMemberInformation.setText(memberLoginInformation);
    }

    public void initializeEventsTable(){

        eventObservableList = FXCollections.observableArrayList();
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Event, String>("type"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Event, Date>("eventDate"));

    }

    @FXML
    public void scheduleEvent() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLScheduleEvent.fxml"));
            Parent root = loader.load();
            FXMLHistoryEventController historyEventController = new FXMLHistoryEventController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("SGP-CA");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException ex){
            Logger.getLogger(FXMLHistoryEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void clickedImageReturn(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLWorkPlan.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("SGP-CA");
            stage.setScene(scene);
            stage.show();

            Stage stageReturn = (Stage) anchorPaneMain.getScene().getWindow();
            stageReturn.close();


        }catch(IOException ex){
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAcademicEvents(){

        tableViewEvents.getItems().clear();
        try {
            List<Event> eventList = EVENT_DAO.displayAllEvents();
            eventObservableList.addAll(eventList);
            tableViewEvents.setItems(eventObservableList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void refreshAcademicEventsTable(MouseEvent mouseEvent){
        tableViewEvents.getSelectionModel().clearSelection();
    }

    public void clickedEvent(){

        tableViewEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                eventSelected = tableViewEvents.getSelectionModel().getSelectedItem();
                consultEvent(eventSelected);
            }
        });
    }

    public void consultEvent(Event event){

        anchorPaneConsultEvent.setVisible(true);
        textAreaTitleEvent.setText(event.getTitle());
        textAreaDateEvent.setText(String.valueOf(event.getEventDate()));
        textAreaPlaceEvent.setText(event.getPlace());
        textAreaTypeEvent.setText(event.getType());

    }

    public void clickedExitConsultEvent(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de la visualización del evento?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.isPresent()){
            if (action.get() == ButtonType.OK) {

                textAreaTitleEvent.clear();
                textAreaDateEvent.clear();
                textAreaPlaceEvent.clear();
                textAreaTypeEvent.clear();
                anchorPaneConsultEvent.setVisible(false);

            }
        }
    }

    public void clickedConsultConstancies(ActionEvent actionEvent) {

        if (eventSelected != null) {
            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLEventConstancy.fxml"));
                Parent root = fXMLLoader.load();
                FXMLEventConstancyController fxmlEventConstancyController = fXMLLoader.getController();

                fxmlEventConstancyController.setMemberInformation(labelMemberInformation.getText());
                fxmlEventConstancyController.setIdAcademicEvent(eventSelected.getIdAcademicEvent());
                fxmlEventConstancyController.setEventConstancies(eventSelected.getIdAcademicEvent());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("SGP-CA");

                root.setOnMousePressed(event -> {
                    newTargetPaneX = event.getSceneX();
                    newTargetPaneY = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - newTargetPaneX);
                    stage.setY(event.getScreenY() - newTargetPaneY);
                });

                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(event -> fxmlEventConstancyController.clickedExitConsultConstancies(actionEvent));

                Stage stageClose = (Stage) buttonEventConstancies.getScene().getWindow();
                stageClose.close();

            } catch (IOException ex) {
                Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
