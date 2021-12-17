package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLMenuEvidencesController implements Initializable {

    private double newTargetPaneX;
    private double newTargetPaneY;
    private static String memberInformation;

    @FXML private JFXButton buttonExit;
    @FXML private AnchorPane anchorPaneMain;
    @FXML private Label labelMemberInformation;

    @Override
    public void initialize(URL url, ResourceBundle resources) {

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

    @FXML
    void clickedArticle(ActionEvent actionEventevent) {
        try{

            FXMLLoader fXmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLArticleList.fxml"));
            Parent root = fXmlLoader.load();
            FXMLArticleListController controller = fXmlLoader.getController();
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
            Logger.getLogger(FXMLArticleListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void clickedBook(ActionEvent actionEventevent) {
        try{

            FXMLLoader fXmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLBookList.fxml"));
            Parent root = fXmlLoader.load();
            FXMLBookListController controller = fXmlLoader.getController();
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
            Logger.getLogger(FXMLBookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void clickedPrototype(ActionEvent actionEventevent) {
        try{

            FXMLLoader fXmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLPrototypeList.fxml"));
            Parent root = fXmlLoader.load();
            FXMLPrototypeListController controller = fXmlLoader.getController();
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
            Logger.getLogger(FXMLPrototypeListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void clickedReceptionWord(ActionEvent actionEventevent) {
        try{

            FXMLLoader fXmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLReceptionWorkList.fxml"));
            Parent root = fXmlLoader.load();
            FXMLReceptionWorkListController controller = fXmlLoader.getController();
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
            Logger.getLogger(FXMLReceptionWorkListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void clickedExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de la consulta de evidencias  del Cuerpo Académico?");
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
            Logger.getLogger(FXMLMenuEvidencesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
