package controller;

import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import businesslogic.PreliminaryProjectDAO;
import com.jfoenix.controls.JFXButton;
import domain.Member;
import domain.PreliminaryProject;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLPreliminaryProjectListController implements Initializable{

    private final PreliminaryProjectDAO PRELIMINARYPROJECT_DAO = new PreliminaryProjectDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private ObservableList<PreliminaryProject> preliminaryProjectObservableList;
    private double newTargetPaneX;
    private double newTargetPaneY;
    private static String memberInformation;

    @FXML
    private TableView<PreliminaryProject> tableViewPreliminaryProjects;
    @FXML
    private TableColumn<PreliminaryProject, String> tableColumnPreliminaryProjectTitle;
    @FXML
    private JFXButton buttonModifyPreliminaryProject;
    @FXML
    private JFXButton buttonAddPreliminaryProject;
    @FXML
    private JFXButton buttonExitConsultPreliminaryProject;
    @FXML
    private JFXButton buttonRemovePreliminaryProject;
    @FXML
    private Label labelMemberInformation;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        initializePreliminaryProjectsTable();
        setPreliminaryProjectsInformation();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
            try {
                Member member = MEMBER_DAO.findMemberByCurp(getCurpMember());
                if (!(member.getAcademicRole().equals("Responsable"))) {
                    buttonRemovePreliminaryProject.setDisable(true);
                    buttonModifyPreliminaryProject.setDisable(true);
                }else{
                    buttonRemovePreliminaryProject.setDisable(false);
                    buttonModifyPreliminaryProject.setDisable(false);
                }
            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }
        }

    }

    public void setMemberInformation(String memberLoginInformation){
        memberInformation = memberLoginInformation;
        labelMemberInformation.setText(memberLoginInformation);
    }

    public String getCurpMember(){
        String memberInformation = labelMemberInformation.getText();
        StringBuilder curpMember = new StringBuilder();
        boolean curpMemberComplete = false;
        boolean passCharacter = false;
        int curpMemberCharacter = 0;

        do{
            if((memberInformation.charAt(curpMemberCharacter) == '[') || passCharacter){
                curpMemberCharacter++;
                passCharacter = true;
                if(memberInformation.charAt(curpMemberCharacter) != ']'){
                    curpMember.append(memberInformation.charAt(curpMemberCharacter));
                }else{
                    curpMemberComplete = true;
                }
            }else{
                curpMemberCharacter++;
            }
        }while(!curpMemberComplete);

        return curpMember.toString().trim();
    }

    public void restrictAccess(String curp){

        try {
            Member member = MEMBER_DAO.findMemberByCurp(curp);
            if (!(member.getAcademicRole().equals("Responsable"))) {
                buttonRemovePreliminaryProject.setDisable(true);
                buttonModifyPreliminaryProject.setDisable(true);
            }else{
                buttonRemovePreliminaryProject.setDisable(false);
                buttonModifyPreliminaryProject.setDisable(false);
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void initializePreliminaryProjectsTable(){

        preliminaryProjectObservableList = FXCollections.observableArrayList();
        tableColumnPreliminaryProjectTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

    }

    public void setPreliminaryProjectsInformation(){

        try {
            List<PreliminaryProject> preliminaryProjectList = PRELIMINARYPROJECT_DAO.displayAllPreliminaryProjects();
            preliminaryProjectObservableList.addAll(preliminaryProjectList);
            tableViewPreliminaryProjects.setItems(preliminaryProjectObservableList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void clickedRemovePreliminaryProject(ActionEvent actionEvent){

        PreliminaryProject preliminaryProject =  tableViewPreliminaryProjects.getSelectionModel().getSelectedItem();

        if(preliminaryProject!=null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Deseas eliminar este anteproyecto del sistema?");
            alert.setContentText("La información del anteproyecto no se podrá recuperar");
            alert.setTitle("Confirmación");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.isPresent()) {
                if (action.get() == ButtonType.OK) {

                    try {
                        preliminaryProjectObservableList.remove(preliminaryProject);
                        boolean operationCorrect = PRELIMINARYPROJECT_DAO.deletePreliminaryProject(preliminaryProject.getIdPreliminaryProyect());
                        tableViewPreliminaryProjects.refresh();

                        if (operationCorrect) {
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Confirmación");
                            alerta.setHeaderText("El anteproyecto ha sido eliminado con éxico");
                            ButtonType confirmacion = new ButtonType("Ok");
                            alerta.getButtonTypes().setAll(confirmacion);
                            alerta.showAndWait();
                        } else {
                            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                            alertWarning.setHeaderText("Base de datos no disponible por el momento");
                            alertWarning.setContentText(null);
                            alertWarning.setTitle("Error en conexión");
                            alertWarning.showAndWait();
                        }
                    }catch(BusinessLogicException ex){
                        ALERT_INTERFACE.openAlertFailedInsert();
                    }
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna anteproyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void clickedModifyPreliminaryProject(ActionEvent actionEvent){

        PreliminaryProject preliminaryProject =  tableViewPreliminaryProjects.getSelectionModel().getSelectedItem();

        if(preliminaryProject!=null){

            try{

                String idPreliminaryProject = preliminaryProject.getIdPreliminaryProyect();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProject.fxml"));
                Parent root = fxmlLoader.load();
                FXMLPreliminaryProjectController fxmlPreliminaryProjectController = fxmlLoader.getController();

                fxmlPreliminaryProjectController.setIdPreliminaryProject(idPreliminaryProject);
                fxmlPreliminaryProjectController.initializePreliminaryProjectModify();

                fxmlPreliminaryProjectController.setMemberInformation(labelMemberInformation.getText());


                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);

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

                Stage stageClose = (Stage) buttonModifyPreliminaryProject.getScene().getWindow();
                stageClose.close();

            } catch (IOException ex) {
                Logger.getLogger(FXMLPreliminaryProjectListController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna anteproyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void clickedExit(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                Stage stage = (Stage) buttonExitConsultPreliminaryProject.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void refreshPreliminaryProjectsTable(MouseEvent mouseEvent){
        tableViewPreliminaryProjects.getSelectionModel().clearSelection();
    }

    public void clickedAddPreliminaryProject(){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProject.fxml"));
            Parent root = fxmlLoader.load();
            FXMLPreliminaryProjectController controller = fxmlLoader.getController();
            controller.setMemberInformation(labelMemberInformation.getText());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

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

            stage.setOnCloseRequest(event -> controller.clickedExitAddPreliminaryProject());

            Stage stageClose = (Stage) buttonAddPreliminaryProject.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPreliminaryProjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedConsultPreliminaryProject(ActionEvent actionEvent){

        PreliminaryProject preliminaryProject = tableViewPreliminaryProjects.getSelectionModel().getSelectedItem();

        if(preliminaryProject!=null){
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
                Logger.getLogger(FXMLPreliminaryProjectListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna anteproyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }


    }



}
