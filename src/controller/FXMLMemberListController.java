package controller;

import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FXMLMemberListController implements Initializable{

    @FXML private TableView<Member> tableViewMembers;
    @FXML private TableColumn<Member, String> tableColumnName;
    @FXML private TableColumn<Member, String>tableColumnEmail;
    @FXML private TableColumn<Member, String> tableColumnTelefono;
    @FXML private TableColumn<Member, String> tableColumnRole;
    @FXML private JFXButton buttonExit;
    @FXML private AnchorPane anchorPaneMain;
    @FXML private Label labelMemberInformation;
    @FXML private JFXButton buttonRegister;
    @FXML private JFXButton buttonRemove;

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private ObservableList<Member> memberObservableList;
    private double newTargetPaneX;
    private double newTargetPaneY;
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private static String memberInformation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        memberObservableList = FXCollections.observableArrayList();
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Member, String>("institutionalMail"));
        tableColumnTelefono.setCellValueFactory(new PropertyValueFactory<Member, String>("telephoneNumber"));
        tableColumnRole.setCellValueFactory(new PropertyValueFactory<Member, String>("academicRole"));
        displayAll();
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
                buttonRegister.setDisable(true);
            }else{
                buttonRemove.setDisable(false);
                buttonRegister.setDisable(false);
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @FXML
    private void displayAll(){

        tableViewMembers.getItems().clear();
        try{
            List<Member> memberList = MEMBER_DAO.displayAllMembers();
            memberObservableList.addAll(memberList);

            tableViewMembers.setItems(memberObservableList);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @FXML public void clickedRemoveMember(ActionEvent actionEvent){

        Member member =  tableViewMembers.getSelectionModel().getSelectedItem();

        if(member!=null){

            Alert memberConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            memberConfirmation.setTitle("Eliminación");
            memberConfirmation.setHeaderText("¿Deseas continuar con la eliminación del miembro en el sistema?");
            memberConfirmation.setContentText("Sus evidencias continuarán registradas en la base de datos");
            Optional<ButtonType> confirmation = memberConfirmation.showAndWait();
            if(confirmation.get() == ButtonType.OK){
                try{
                    MEMBER_DAO.deleteMember(member.getCurp());
                    displayAll();
                } catch (BusinessLogicException ex) {
                    ALERT_INTERFACE.openAlertFailedInsert();
                }

            }


        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningún miembro");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    @FXML public void clickedExit(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de la consulta de los miembros del Cuerpo Académico?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            returnToWorkPlanPane();
        }
    }

    @FXML
    public void returnToWorkPlanPane(){

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
            Logger.getLogger(FXMLMemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML public void clickedRegisterNewMember(ActionEvent actionEvent){

        try{

            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("../user/FXMLMember.fxml"));
            Parent root = fXMLLoader.load();
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
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXMLMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
