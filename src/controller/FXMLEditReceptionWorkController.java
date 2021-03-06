package controller;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import businesslogic.ReceptionWorkDAO;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.InvestigationProject;
import domain.ReceptionWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLEditReceptionWorkController implements Initializable {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private ComboBox<String> comboBoxProject;

    @FXML
    private JFXTextField textFieldTitle;

    @FXML
    private JFXTextField textFieldAdacemicDegree;

    @FXML
    private JFXTextField textFieldCondition;

    @FXML
    private JFXTextField textFieldStudent;

    private final ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
    private  final InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        textFieldTitle.setText(FXMLReceptionWorkListController.receptionWorkSelected.getTitle());
        textFieldAdacemicDegree.setText(FXMLReceptionWorkListController.receptionWorkSelected.getAcademicdegree());
        textFieldCondition.setText(FXMLReceptionWorkListController.receptionWorkSelected.getCondition());
        textFieldStudent.setText(FXMLReceptionWorkListController.receptionWorkSelected.getStudentName());
        comboBoxProject.setValue(FXMLReceptionWorkListController.receptionWorkSelected.getIdInvestigationProject());
        setComboBoxWithProjects();
        validateTextReceptionWork();
    }

    public void setComboBoxWithProjects(){

        ObservableList<String> projectsObservableList = FXCollections.observableArrayList();
        try{
            List<InvestigationProject> projectsList = investigationProjectDAO.displayAllinvestigationProjects();

            for(InvestigationProject investigationProject: projectsList){
                String idInvestigationProject = investigationProject.getIdInvestigationProject();
                String title = investigationProject.getTitle();
                String comboBoxWorkPlans = idInvestigationProject;
                projectsObservableList.add(comboBoxWorkPlans);
            }
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

        comboBoxProject.setItems(projectsObservableList);
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

    @FXML
    private void clickRegister(ActionEvent event) {
        boolean validateTitle = textFieldTitle.validate();
        boolean validateAcademicDegree = textFieldAdacemicDegree.validate();
        boolean validateCondition = textFieldCondition.validate();
        boolean validateStudent = textFieldStudent.validate();
        try{
            ReceptionWork receptionWork = receptionWorkDAO.foundReceptionWorkByID(FXMLReceptionWorkListController.receptionWorkSelected.getIdReceptionWork());
            if((validateTitle == true) && (validateAcademicDegree == true) && (validateCondition == true)
                    && (validateStudent == true) && (comboBoxProject.getValue()!=null)){
                receptionWork.setTitle(textFieldTitle.getText());
                receptionWork.setAcademicdegree(textFieldAdacemicDegree.getText());
                receptionWork.setIdInvestigationProject(comboBoxProject.getValue());
                receptionWork.setCondition(textFieldCondition.getText());
                receptionWork.setStudentName(textFieldStudent.getText());


                receptionWorkDAO.updateReceptionWork(receptionWork);

                ALERT_INTERFACE.openAlertSuccesfulInsert();

            }else{
                ALERT_INTERFACE.openAlertInvalidData();
            }

        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void validateTextReceptionWork(){

        final String TEXT_PATTERN = "^[0-9a-zA-Z??-??\\u00f1\\u00d1]{1,}[0-9\\sa-zA-Z??-??\\u00f1\\u00d1.:',_-]{0,}";

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("Campo obligatorio");

        RegexValidator textValidator = new RegexValidator();
        textValidator.setRegexPattern(TEXT_PATTERN);
        textValidator.setMessage("Campo invalido");

        StringLengthValidator lengthValidator100 = new StringLengthValidator(100);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        StringLengthValidator lengthValidator200 = new StringLengthValidator(300);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        textFieldTitle.getValidators().add(requiredValidator);
        textFieldTitle.getValidators().add(textValidator);
        textFieldTitle.getValidators().add(lengthValidator100);
        textFieldAdacemicDegree.getValidators().add(requiredValidator);
        textFieldAdacemicDegree.getValidators().add(textValidator);
        textFieldAdacemicDegree.getValidators().add(lengthValidator100);
        textFieldStudent.getValidators().add(requiredValidator);
        textFieldStudent.getValidators().add(textValidator);
        textFieldStudent.getValidators().add(lengthValidator100);
        textFieldCondition.getValidators().add(requiredValidator);
        textFieldCondition.getValidators().add(textValidator);
        textFieldCondition.getValidators().add(lengthValidator200);
    }



}