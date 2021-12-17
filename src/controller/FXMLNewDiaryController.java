package controller;

import businesslogic.DiaryDAO;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import domain.Diary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLNewDiaryController implements Initializable{

    @FXML private AnchorPane scenePane;
    @FXML private JFXTextField textFieldNumber;
    @FXML private JFXTextField textFieldEstimatedTime;
    @FXML private JFXTextField textFieldRealTime;
    @FXML private JFXTextField textFieldDiscussionLeader;
    @FXML private JFXTextArea textAreaPointDiscussed;

    private DiaryDAO diaryDAO = new DiaryDAO();
    private AlertInterface alertInterface = new AlertInterface();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldNumber.setText(String.valueOf(diaryDAO.getMaxNumberOfDiaries()+1));
        validateData();
    }


    @FXML
    private void clickAccept(){

        if(!(textAreaPointDiscussed.getText().equals("") || textFieldRealTime.getText().equals("") ||
                textFieldEstimatedTime.getText().equals("") || textFieldDiscussionLeader.getText().equals("") ||textAreaPointDiscussed.validate() == false
                || textFieldDiscussionLeader.validate() == false || textFieldEstimatedTime.validate() == false || textFieldRealTime.validate() ==  false)){

            Diary diary = new Diary(
                    Integer.parseInt(textFieldNumber.getText()),
                    textFieldDiscussionLeader.getText(),
                    Integer.parseInt(textFieldRealTime.getText()),
                    Integer.parseInt(textFieldEstimatedTime.getText()),
                    textAreaPointDiscussed.getText(),null
            );

            diaryDAO.addDiary(diary);
            textAreaPointDiscussed.setText("");
            textFieldDiscussionLeader.setText("");
            textFieldRealTime.setText("");
            textFieldEstimatedTime.setText("");
            textFieldNumber.setText(String.valueOf(diaryDAO.getMaxNumberOfDiaries()+1));
            alertInterface.openAlertSuccesfulInsert();
            exit();
        }else{
            alertInterface.openAlertWarningIncompleteData();
        }

    }

    @FXML
    private void exit(){
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }


    private final String TIME_VALIDATOR_PATTERN = "^([1-9]|[1-9][0-9]|[1-9][0-9][0-9])$";
    private final String NAMES_VALIDATOR_PATTERN = "^([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\']+[\\s])+([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])+" + "[\\s]?([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])?$";

    public void validateData(){
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Este campo es obligatorio");

        RegexValidator timeValidator = new RegexValidator();
        timeValidator.setRegexPattern(TIME_VALIDATOR_PATTERN);
        timeValidator.setMessage("ingrese el tiempo en minutos");

        RegexValidator namesValidator = new RegexValidator();
        namesValidator.setRegexPattern(NAMES_VALIDATOR_PATTERN);
        namesValidator.setMessage("Ingrese un nombre valido");

        textAreaPointDiscussed.getValidators().add(fieldValidator);
        textFieldRealTime.getValidators().add(timeValidator);
        textFieldEstimatedTime.getValidators().add(timeValidator);
        textFieldDiscussionLeader.getValidators().add(namesValidator);

        textAreaPointDiscussed.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue){
                textAreaPointDiscussed.validate();
            }
        }));

        textFieldRealTime.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                textFieldRealTime.validate();
            }
        });

        textFieldEstimatedTime.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                textFieldEstimatedTime.validate();
            }
        });

        textFieldDiscussionLeader.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue){
                textFieldDiscussionLeader.validate();
            }
        }));
    }

}
