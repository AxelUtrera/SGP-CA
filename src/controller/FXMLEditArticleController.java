package controller;

import businesslogic.ArticleDAO;
import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.Article;
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

public class FXMLEditArticleController implements Initializable {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private ComboBox<String> comboBoxProject;

    @FXML
    private JFXTextField textFieldTitle;

    @FXML
    private JFXTextField textFieldAdacemicDegree;

    @FXML
    private JFXTextField textFieldISICI;

    @FXML
    private JFXTextField textFieldMagazine;

    @FXML
    private JFXTextField textFieldEditorial;

    @FXML
    private JFXTextArea textAreaDescription;

    private final ArticleDAO ARTICLE_DAO = new ArticleDAO();

    private  final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();


    private final AlertInterface ALERT_INTERFACE = new AlertInterface();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldTitle.setText(FXMLArticleListController.articleSelected.getTitle());
        textFieldAdacemicDegree.setText(FXMLArticleListController.articleSelected.getAcademicdegree());
        textFieldISICI.setText(FXMLArticleListController.articleSelected.getSici());
        textFieldEditorial.setText(FXMLArticleListController.articleSelected.getEditorialMagazine());
        textFieldMagazine.setText(String.valueOf(FXMLArticleListController.articleSelected.getMagazineName()));
        textAreaDescription.setText(FXMLArticleListController.articleSelected.getDescription());
        comboBoxProject.setValue(FXMLArticleListController.articleSelected.getIdInvestigationProject());
        setComboBoxWithProjects();
        validateText();
    }

    public void setComboBoxWithProjects(){

        ObservableList<String> projectsObservableList = FXCollections.observableArrayList();
        try {
            List<InvestigationProject> projectsList = INVESTIGATIONPROJECT_DAO.displayAllinvestigationProjects();

            for (InvestigationProject investigationProject : projectsList) {
                String idInvestigationProject = investigationProject.getIdInvestigationProject();
                String comboBoxWorkPlans = idInvestigationProject;
                projectsObservableList.add(comboBoxWorkPlans);
            }

            comboBoxProject.setItems(projectsObservableList);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @FXML
    private void clickCancel(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar la actualización ?");
        alert.setContentText("No se realizará ningún cambio en el sistema");
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
        boolean validateSICI = textFieldISICI.validate();
        boolean validateMagazine = textFieldMagazine.validate();
        boolean validateEditorial = textFieldEditorial.validate();
        boolean validateDescription = textAreaDescription.validate();
        try{
            Article article = ARTICLE_DAO.foundArticleBySICI(FXMLArticleListController.articleSelected.getSici());

            if((validateTitle == true) && (validateAcademicDegree == true) && (validateSICI == true) &&
                    (validateMagazine == true) && (validateEditorial == true) && (validateDescription == true) && (comboBoxProject.getValue()!=null)){
                article.setTitle(textFieldTitle.getText());
                article.setAcademicdegree(textFieldAdacemicDegree.getText());
                article.setIdInvestigationProject(comboBoxProject.getValue());
                article.setSici(textFieldISICI.getText());
                article.setMagazineName(textFieldMagazine.getText());
                article.setEditorialMagazine(textFieldEditorial.getText());
                article.setDescription(textAreaDescription.getText());

                if(ARTICLE_DAO.foundArticleBySICI(textFieldISICI.getText())== null) {
                    ARTICLE_DAO.updateArticle(article);
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

        final String TEXT_PATTERN = "^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[0-9\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("Campo obligatorio");

        RegexValidator textValidator = new RegexValidator();
        textValidator.setRegexPattern(TEXT_PATTERN);
        textValidator.setMessage("Campo invalido");

        StringLengthValidator lengthValidator100 = new StringLengthValidator(100);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        StringLengthValidator lengthValidator45 = new StringLengthValidator(45);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        StringLengthValidator lengthValidator300 = new StringLengthValidator(300);
        lengthValidator100.setMessage("Exediste el numero de caracteres");

        textFieldTitle.getValidators().add(requiredValidator);
        textFieldTitle.getValidators().add(textValidator);
        textFieldTitle.getValidators().add(lengthValidator100);
        textFieldAdacemicDegree.getValidators().add(requiredValidator);
        textFieldAdacemicDegree.getValidators().add(textValidator);
        textFieldAdacemicDegree.getValidators().add(lengthValidator100);
        textFieldISICI.getValidators().add(requiredValidator);
        textFieldISICI.getValidators().add(textValidator);
        textFieldISICI.getValidators().add(lengthValidator45);
        textFieldMagazine.getValidators().add(requiredValidator);
        textFieldMagazine.getValidators().add(textValidator);
        textFieldMagazine.getValidators().add(lengthValidator100);
        textFieldEditorial.getValidators().add(requiredValidator);
        textFieldEditorial.getValidators().add(textValidator);
        textFieldEditorial.getValidators().add(lengthValidator100);
        textAreaDescription.getValidators().add(requiredValidator);
        textAreaDescription.getValidators().add(textValidator);
        textAreaDescription.getValidators().add(lengthValidator300);
    }



}