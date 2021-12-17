package controller;


import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import businesslogic.PrototypeDAO;
import com.jfoenix.controls.JFXButton;
import domain.Member;
import domain.Prototype;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLPrototypeListController implements Initializable {

    @FXML private JFXButton buttonExit;
    @FXML private  TableView<Prototype> tableViewEvidence;
    @FXML private TableColumn<Prototype, String> tableColumnName;
    @FXML private JFXButton buttonRemove;
    @FXML private JFXButton buttonEdit;
    @FXML private Label labelMemberInformation;

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private  final PrototypeDAO PROTOTYPE_DAO = new PrototypeDAO();
    private  ObservableList<Prototype> evidencesObservableList;
    private double newTargetPaneX;
    private double newTargetPaneY;
    public  static Prototype prototypeSelected;
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private static String memberInformation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        evidencesObservableList = FXCollections.observableArrayList();
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Prototype, String>("title"));
        updateList();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
        }
    }

    public void setMemberInformation(String memberLoginInformation){
        memberInformation = memberLoginInformation;
        labelMemberInformation.setText(memberLoginInformation);
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

        tableViewEvidence.getItems().clear();
        try{
            List<Prototype>  evidencesList = PROTOTYPE_DAO.displayAllPrototypes();
            evidencesObservableList.addAll(evidencesList);
            tableColumnName.setCellValueFactory(new PropertyValueFactory<Prototype, String>("title"));
            tableViewEvidence.setItems(evidencesObservableList);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @FXML public void clickedRemove(ActionEvent actionEvent){

        Prototype prototype =  tableViewEvidence.getSelectionModel().getSelectedItem();

        if(prototype!=null){

            Alert memberConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            memberConfirmation.setTitle("Eliminación");
            memberConfirmation.setHeaderText("¿Deseas continuar con la eliminación de la evidencia en el sistema?");
            Optional<ButtonType> confirmation = memberConfirmation.showAndWait();
            if(confirmation.get() == ButtonType.OK){
                try{
                    PROTOTYPE_DAO.deletePrototype(prototype.getIdPrototype());
                    updateList();
                } catch (BusinessLogicException ex) {
                    ALERT_INTERFACE.openAlertFailedInsert();
                }
            }


        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningúna evidencia");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    @FXML public void clickedExit(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de la consulta de las evidencias del Cuerpo Académico?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage)buttonExit.getScene().getWindow();
            stage.close();
        }
    }

    @FXML public void newRegister(ActionEvent actionEvent){

        try{

            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLPrototype.fxml"));
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
            Logger.getLogger(FXMLPrototypeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML public void clickedEdit (ActionEvent actionEvent) {

        prototypeSelected = tableViewEvidence.getSelectionModel().getSelectedItem();

        if (prototypeSelected != null) {
            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLEditPrototype.fxml"));
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
                Logger.getLogger(FXMLEditPrototypeController.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna evidencia");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    @FXML public void clickedConsult(ActionEvent actionEvent){

        prototypeSelected = tableViewEvidence.getSelectionModel().getSelectedItem();

        if (prototypeSelected != null) {
            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLConsultPrototype.fxml"));
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
                Logger.getLogger(FXMLConsultPrototypeController.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna evidencia");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }



}
