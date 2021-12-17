package controller;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import domain.InvestigationProject;
import domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLInvestigationProjectListController implements Initializable {

    private  ObservableList<InvestigationProject> investigationProjectObservableList;
    private double newTargetPaneX;
    private double newTargetPaneY;
    public  static InvestigationProject investigationProjectSelected;
    public static String memberInformation;

    @FXML private JFXButton buttonExit;
    @FXML private  TableView<InvestigationProject> tableViewProjects;
    @FXML private TableColumn<InvestigationProject, String> tableColumnName;
    @FXML private JFXButton buttonPreliminaryProjectList;
    @FXML private AnchorPane anchorPaneMain;
    @FXML private JFXButton buttonRemove;
    @FXML private JFXButton buttonEdit;
    @FXML private Label labelMemberInformation;

    private  final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        investigationProjectObservableList = FXCollections.observableArrayList();
        tableColumnName.setCellValueFactory(new PropertyValueFactory<InvestigationProject, String>("title"));
        updateList();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
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
                buttonRemove.setDisable(true);
                buttonEdit.setDisable(true);
            }else{
                buttonRemove.setDisable(false);
                buttonEdit.setDisable(false);
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @FXML public  void updateList(){

        try {
            tableViewProjects.getItems().clear();
            List<InvestigationProject> investigationProjectList = INVESTIGATIONPROJECT_DAO.displayAllinvestigationProjects();
            investigationProjectObservableList.addAll(investigationProjectList);
            tableColumnName.setCellValueFactory(new PropertyValueFactory<InvestigationProject, String>("title"));
            tableViewProjects.setItems(investigationProjectObservableList);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @FXML public void clickedRemoveProject(ActionEvent actionEvent){

        InvestigationProject investigationProject =  tableViewProjects.getSelectionModel().getSelectedItem();

        if(investigationProject!=null){

            Alert memberConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            memberConfirmation.setTitle("Eliminación");
            memberConfirmation.setHeaderText("¿Deseas continuar con la eliminación del proyecto de investigación en el sistema?");
            Optional<ButtonType> confirmation = memberConfirmation.showAndWait();
            if(confirmation.get() == ButtonType.OK){
                try {
                    INVESTIGATIONPROJECT_DAO.deleteInvestigationProject(investigationProject.getIdInvestigationProject());
                    updateList();
                }catch (BusinessLogicException ex) {
                    ALERT_INTERFACE.openAlertFailedInsert();
                }

            }


        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningún proyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    @FXML public void clickedExit(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de la consulta de los proyectos de investigación del Cuerpo Académico?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {

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
    }

    @FXML public void newRegister(ActionEvent actionEvent){

        try{

            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLInvestigationProject.fxml"));
            Parent root = fXMLLoader.load();
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

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML public void editProject(ActionEvent actionEvent) {

        investigationProjectSelected = tableViewProjects.getSelectionModel().getSelectedItem();

        if (investigationProjectSelected != null) {
            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLEditInvestigationProject.fxml"));
                Parent root = fXMLLoader.load();
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


            } catch (IOException ex) {
                Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningún proyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    @FXML public void consultarProject(ActionEvent actionEvent){

        investigationProjectSelected = tableViewProjects.getSelectionModel().getSelectedItem();

        if (investigationProjectSelected != null) {
            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLConsultInvestigationProject.fxml"));
                Parent root = fXMLLoader.load();
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


            } catch (IOException ex) {
                Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningún proyecto");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void clickedPreliminaryProjects(ActionEvent actionEvent){

        try{

            FXMLLoader fXmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProjectList.fxml"));
            Parent root = fXmlLoader.load();
            FXMLPreliminaryProjectListController controller = fXmlLoader.getController();
            controller.setMemberInformation(labelMemberInformation.getText());
            controller.restrictAccess(getCurpMember());

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

        } catch (IOException ex) {
            Logger.getLogger(FXMLInvestigationProjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
