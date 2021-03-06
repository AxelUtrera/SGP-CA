package controller;

import businesslogic.InvestigationProjectDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.InvestigationProject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLEditInvestigationProjectController implements Initializable {


    @FXML private AnchorPane scenePane;

    @FXML private JFXTextField textFieldTitulo;

    @FXML private JFXTextArea textAreaDescription;

    @FXML private JFXDatePicker datePickerEndDate;

    @FXML private JFXDatePicker datePickerStartDate;


    private final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    private InvestigationProject project = FXMLInvestigationProjectListController.investigationProjectSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldTitulo.setText(project.getTitle());
        textAreaDescription.setText(project.getDescription());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate startLocalDate = LocalDate.parse(simpleDateFormat.format(project.getStartDate()));
        LocalDate endLocalDate = LocalDate.parse(simpleDateFormat.format(project.getEndDate()));
        datePickerEndDate.setValue(endLocalDate);
        datePickerStartDate.setValue(startLocalDate);
        validateText();
    }

    @FXML public void register() throws SQLException {

        boolean validateTitle = textFieldTitulo.validate();
        boolean validateDescription = textAreaDescription.validate();
        InvestigationProject investigationProject = INVESTIGATIONPROJECT_DAO.foundInvestigationProjectByidInvestigationProject(project.getIdInvestigationProject());


        if((validateStartDate(Date.valueOf(datePickerStartDate.getValue()),Date.valueOf(datePickerEndDate.getValue()))) ==true && (validateEndDate(Date.valueOf(datePickerEndDate.getValue()))) == true && (validateTitle == true) && (validateDescription==true)) {

            investigationProject.setTitle(textFieldTitulo.getText());
            investigationProject.setStartDate(Date.valueOf(datePickerStartDate.getValue()));
            investigationProject.setStartDate(Date.valueOf(datePickerEndDate.getValue()));
            investigationProject.setDescription(textAreaDescription.getText());

            INVESTIGATIONPROJECT_DAO.updateInvestigationProject(investigationProject);
            ALERT_INTERFACE.openAlertSuccesfulUpdate();
        }else{
            ALERT_INTERFACE.openAlertInvalidData();
        }
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();

    }

    @FXML private void clickCancel(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("??Deseas cancelar el registro?");
        alert.setContentText("No se realizar?? ning??n cambio en el sistema");
        alert.setTitle("Cancelar");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }

    }

    public boolean validateStartDate(java.util.Date startDate, java.util.Date endDate){

        boolean correctStarDate=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date actualDate = new java.util.Date();
        try {
            java.util.Date actualDateSimpleFormat = dateFormat.parse(dateFormat.format(actualDate));

            if ((actualDateSimpleFormat.before(startDate) || actualDateSimpleFormat.equals(startDate)) && startDate.before(endDate)) {
                correctStarDate = true;
            }
        }catch (ParseException parseException){
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, parseException);
        }
        return correctStarDate;
    }

    public boolean validateEndDate(java.util.Date endDate){

        boolean correctEndDate =false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date actualDate = new java.util.Date();
        try{
            java.util.Date actualDateSimpleFormat = dateFormat.parse(dateFormat.format(actualDate));

            if(actualDateSimpleFormat.before(endDate)){
                correctEndDate=true;
            }
        }catch (ParseException parseException){
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, parseException);
        }

        return correctEndDate;
    }

    public void validateText(){

        final String TEXT_PATTERN = "^[0-9a-zA-Z??-??\\u00f1\\u00d1]{1,}[0-9\\sa-zA-Z??-??\\u00f1\\u00d1.:',_-]{0,}";

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("Campo obligatorio");

        RegexValidator textValidator = new RegexValidator();
        textValidator.setRegexPattern(TEXT_PATTERN);
        textValidator.setMessage("Campo invalido");

        StringLengthValidator lengthValidator100 = new StringLengthValidator(100);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        StringLengthValidator lengthValidator300 = new StringLengthValidator(300);
        lengthValidator300.setMessage("Exediste el numero de caracteres");


        textFieldTitulo.getValidators().add(requiredValidator);
        textFieldTitulo.getValidators().add(textValidator);
        textFieldTitulo.getValidators().add(lengthValidator100);
        textAreaDescription.getValidators().add(requiredValidator);
        textAreaDescription.getValidators().add(textValidator);
        textAreaDescription.getValidators().add(lengthValidator300);

    }

}
