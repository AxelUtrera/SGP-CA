package controller;

import businesslogic.BookDAO;
import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.Book;
import domain.InvestigationProject;
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

public class FXMLEditBookController implements Initializable {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private ComboBox<String> comboBoxProject;

    @FXML
    private JFXTextField textFieldTitle;

    @FXML
    private JFXTextField textFieldAdacemicDegree;

    @FXML
    private JFXTextField textFieldISBN;

    @FXML
    private JFXTextField textFieldEditorial;

    @FXML
    private JFXTextField textFieldEditionNumber;

    private final BookDAO BOOK_DAO = new BookDAO();


    private  final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();


    private final AlertInterface ALERT_INTERFACE = new AlertInterface();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldTitle.setText(FXMLBookListController.bookSelected.getTitle());
        textFieldAdacemicDegree.setText(FXMLBookListController.bookSelected.getAcademicdegree());
        textFieldISBN.setText(FXMLBookListController.bookSelected.getIsbn());
        textFieldEditorial.setText(FXMLBookListController.bookSelected.getEditorial());
        textFieldEditionNumber.setText(String.valueOf(FXMLBookListController.bookSelected.getEditionNumber()));
        comboBoxProject.setValue(FXMLBookListController.bookSelected.getIdInvestigationProject());
        setComboBoxWithProjects();
        validateText();
    }

    public void setComboBoxWithProjects(){

        ObservableList<String> projectsObservableList = FXCollections.observableArrayList();
        try{
            List<InvestigationProject> projectsList = INVESTIGATIONPROJECT_DAO.displayAllinvestigationProjects();

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
        alert.setHeaderText("??Deseas cancelar la actualizaci??n ?");
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
        boolean validateISBN = textFieldISBN.validate();
        boolean validateEditorial = textFieldEditorial.validate();
        boolean validateEditionNumber = textFieldEditionNumber.validate();
        try{
            Book book = BOOK_DAO.foundBookByIsbn(FXMLBookListController.bookSelected.getIsbn());

            if((validateTitle == true) && (validateAcademicDegree == true) && (validateISBN == true) &&
                    (validateEditorial == true) && (validateEditionNumber == true) && (comboBoxProject.getValue()!=null)){
                int editionNumber = Integer.parseInt (textFieldEditionNumber.getText());
                book.setTitle(textFieldTitle.getText());
                book.setAcademicdegree(textFieldAdacemicDegree.getText());
                book.setIdInvestigationProject(comboBoxProject.getValue());
                book.setIsbn(textFieldISBN.getText());
                book.setEditorial(textFieldEditorial.getText());
                book.setEditionNumber(editionNumber);


                if(BOOK_DAO.foundBookByIsbn(textFieldISBN.getText())== null) {
                    BOOK_DAO.updateBook(book);
                    ALERT_INTERFACE.openAlertSuccesfulInsert();
                }else{
                    ALERT_INTERFACE.openAlertDuplicatedData();
                }

            }else{
                ALERT_INTERFACE.openAlertInvalidData();
            }
            Stage stage = (Stage) scenePane.getScene().getWindow();
            stage.close();

        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void validateText(){

        final String TEXT_PATTERN = "^[0-9a-zA-Z??-??\\u00f1\\u00d1]{1,}[0-9\\sa-zA-Z??-??\\u00f1\\u00d1.:',_-]{0,}";
        final String NUMBER_PATTERN = "^\\d+$";

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("Campo obligatorio");

        RegexValidator textValidator = new RegexValidator();
        textValidator.setRegexPattern(TEXT_PATTERN);
        textValidator.setMessage("Campo invalido");

        RegexValidator numberValidator = new RegexValidator();
        numberValidator.setRegexPattern(NUMBER_PATTERN);
        numberValidator.setMessage("Campo invalido");

        StringLengthValidator lengthValidator100 = new StringLengthValidator(100);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        StringLengthValidator lengthValidator45 = new StringLengthValidator(45);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        textFieldTitle.getValidators().add(requiredValidator);
        textFieldTitle.getValidators().add(textValidator);
        textFieldTitle.getValidators().add(lengthValidator100);
        textFieldAdacemicDegree.getValidators().add(requiredValidator);
        textFieldAdacemicDegree.getValidators().add(textValidator);
        textFieldAdacemicDegree.getValidators().add(lengthValidator100);
        textFieldISBN.getValidators().add(requiredValidator);
        textFieldISBN.getValidators().add(textValidator);
        textFieldISBN.getValidators().add(lengthValidator45);
        textFieldEditorial.getValidators().add(requiredValidator);
        textFieldEditorial.getValidators().add(textValidator);
        textFieldEditorial.getValidators().add(lengthValidator100);
        textFieldEditionNumber.getValidators().add(requiredValidator);
        textFieldEditionNumber.getValidators().add(numberValidator);
        textFieldEditionNumber.getValidators().add(lengthValidator100);

    }



}