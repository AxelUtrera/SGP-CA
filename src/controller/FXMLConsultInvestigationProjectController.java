package controller;

import businesslogic.BusinessLogicException;
import businesslogic.PreliminaryProjectDAO;
import com.jfoenix.controls.JFXButton;
import domain.InvestigationProject;
import domain.PreliminaryProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLConsultInvestigationProjectController implements Initializable {

    private final PreliminaryProjectDAO PRELIMINARYPROJECT_DAO = new PreliminaryProjectDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private InvestigationProject investigationProject = FXMLInvestigationProjectListController.investigationProjectSelected;
    private ObservableList<PreliminaryProject> preliminaryProjectObservableList;
    private double newTargetPaneX;
    private double newTargetPaneY;

    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private TableView<PreliminaryProject> tableViewPreliminaryProjects;
    @FXML
    private TableColumn<PreliminaryProject, String> tableColumnTitle;
    @FXML
    private JFXButton buttonAddPreliminaryProject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaDescription.setText(investigationProject.toString());
        initializePreliminaryProjectsTable();
        setPreliminaryProjectsInformation();
        clickedPreliminaryProject();
    }

    public void initializePreliminaryProjectsTable(){

        preliminaryProjectObservableList = FXCollections.observableArrayList();
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    public void setPreliminaryProjectsInformation(){

        try {
            String idInvestigationProject = investigationProject.getIdInvestigationProject();
            List<PreliminaryProject> preliminaryProjectList = PRELIMINARYPROJECT_DAO.foundPreliminaryProjectsByIdInvestigationProject(idInvestigationProject);
            preliminaryProjectObservableList.addAll(preliminaryProjectList);
            tableViewPreliminaryProjects.setItems(preliminaryProjectObservableList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @FXML
    private void clickExit(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Estás seguro de que deseas salir?");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()){
            if (action.get() == ButtonType.OK) {
                Stage stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
            }
        }

    }


    public void clickedPreliminaryProject() {

        tableViewPreliminaryProjects.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                consultPreliminaryProject(tableViewPreliminaryProjects.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void consultPreliminaryProject(PreliminaryProject preliminaryProject){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLConsultPreliminaryProject.fxml"));
            Parent root = fxmlLoader.load();
            FXMLConsultPreliminaryProjectController fxmlConsultPreliminaryProjectController = fxmlLoader.getController();
            fxmlConsultPreliminaryProjectController.setPreliminaryProjectInformation(preliminaryProject.getIdPreliminaryProyect());

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
            stage.showAndWait();


        }catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedAddPreliminaryProject(ActionEvent actionEvent){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProject.fxml"));
            Parent root = fxmlLoader.load();
            FXMLPreliminaryProjectController fxmlPreliminaryProjectController = fxmlLoader.getController();

            fxmlPreliminaryProjectController.setMemberInformation(FXMLInvestigationProjectListController.memberInformation);


            fxmlPreliminaryProjectController.setIdInvestigationProject(investigationProject.getIdInvestigationProject());

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

            stage.setOnCloseRequest(event -> fxmlPreliminaryProjectController.clickedExitAddPreliminaryProject());

            Stage stageClose = (Stage) buttonAddPreliminaryProject.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPreliminaryProjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshPreliminaryProjectsTable(MouseEvent mouseEvent){
        tableViewPreliminaryProjects.getSelectionModel().clearSelection();
    }



}
