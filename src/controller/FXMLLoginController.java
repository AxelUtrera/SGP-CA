package controller;

import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import domain.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLLoginController implements Initializable {

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private double newTargetPaneX;
    private double newTargetPaneY;

    @FXML
    private Label labelInvalidLogIn;
    @FXML
    private JFXTextField textFieldInstitutionalMail;
    @FXML
    private JFXPasswordField passwordFieldPassword;
    @FXML
    private JFXButton buttonLogin;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        validateFields();
    }

    public void validateFields(){

        final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*(\\.[A-Za-z]{2,})$";

        final RequiredFieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();
        REQUIRED_VALIDATOR.setMessage("Campo obligatorio");

        final RegexValidator EMAIL_VALIDATOR = new RegexValidator();
        EMAIL_VALIDATOR.setRegexPattern(EMAIL_PATTERN);
        EMAIL_VALIDATOR.setMessage("Formato de correo inválido");

        textFieldInstitutionalMail.getValidators().add(REQUIRED_VALIDATOR);
        passwordFieldPassword.getValidators().add(REQUIRED_VALIDATOR);

        textFieldInstitutionalMail.getValidators().add(EMAIL_VALIDATOR);

    }

    public void clickedLogin(ActionEvent actionEvent){

        boolean correctInstitutionalMail = textFieldInstitutionalMail.validate();
        boolean correctPassword = passwordFieldPassword.validate();

        if(correctInstitutionalMail && correctPassword){

            try {
                String institutionalMail = textFieldInstitutionalMail.getText();
                Member member = MEMBER_DAO.findMemberByInstitutionalMail(institutionalMail);

                if (member != null) {

                    String password = passwordFieldPassword.getText();
                    String decryptedPassword = MEMBER_DAO.decryptPassword(institutionalMail);
                    if (member.getInstitutionalMail().equals(institutionalMail) && decryptedPassword.equals(password)) {

                        displayWorkPlanPane(member);

                    } else {
                        labelInvalidLogIn.setVisible(true);
                    }
                } else {
                    labelInvalidLogIn.setVisible(true);
                }
            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }
        }
    }

    public void displayWorkPlanPane(Member member){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLWorkPlan.fxml"));
            Parent root = fxmlLoader.load();
            FXMLWorkPlanController fxmlWorkPlanController = fxmlLoader.getController();

            fxmlWorkPlanController.setMemberInformation(member);

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

            Stage stageClose = (Stage) buttonLogin.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
