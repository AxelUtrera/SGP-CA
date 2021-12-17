package controller;

import businesslogic.BusinessLogicException;
import businesslogic.EventDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import domain.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.ResourceBundle;

public class FXMLScheduleEventController implements Initializable {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    @FXML private AnchorPane principalAnchorPane;
    @FXML private JFXTextField textFieldTittle;
    @FXML private JFXTextField textFieldEventType;
    @FXML private JFXTextField textFieldPlace;
    @FXML private JFXTextField textFieldHour;
    @FXML private JFXDatePicker datePickerDate;


    @FXML
    public void scheduleClicked(){
        AlertInterface alertInterface = new AlertInterface();
        EventDAO eventDAO = new EventDAO();
        Calendar actualDate = Calendar.getInstance();

        try {
            if (!(textFieldTittle.getText().equals("") || textFieldEventType.getText().equals("") ||
                    textFieldPlace.getText().equals("") || textFieldHour.getText().equals("") || datePickerDate.getValue() == null || textFieldEventType.validate() == false
                    || textFieldHour.validate() == false || textFieldPlace.validate() == false || textFieldTittle.validate() == false || datePickerDate.validate() == false)) {

                Event event = new Event(
                        textFieldTittle.getText(),
                        textFieldEventType.getText(),
                        Time.valueOf(textFieldHour.getText() + ":00"),
                        textFieldPlace.getText(),
                        Date.valueOf(datePickerDate.getValue())
                );
                System.out.print(Time.valueOf(textFieldHour.getText() + ":00").toString());


                eventDAO.addEvent(event);
                alertInterface.openAlertSuccesfulInsert();
                exit();


            } else {
                alertInterface.openAlertWarningIncompleteData();
            }
        }catch(BusinessLogicException ex){
        ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @FXML
    public void exit(){
        Stage stage = (Stage) principalAnchorPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validate();
    }



    private final String TIME_VALIDATOR = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

    private void validate(){
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Este campo es obligatorio");

        RegexValidator validatorHours = new RegexValidator();
        validatorHours.setRegexPattern(TIME_VALIDATOR);
        validatorHours.setMessage("Formato incorrecto");

        textFieldTittle.setValidators(fieldValidator);
        textFieldEventType.setValidators(fieldValidator);
        textFieldPlace.setValidators(fieldValidator);
        textFieldHour.setValidators(validatorHours);
        datePickerDate.setValidators(fieldValidator);

        textFieldTittle.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldTittle.validate();
            }
        }));

        textFieldPlace.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldPlace.validate();
            }
        }));

        textFieldEventType.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldEventType.validate();
            }
        }));

        textFieldHour.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldHour.validate();
            }
        }));

        datePickerDate.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                datePickerDate.validate();
            }
        }));


    }
}