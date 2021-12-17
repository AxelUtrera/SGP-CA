package controller;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import businesslogic.MemberDAO;
import businesslogic.PreliminaryProjectDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.PreliminaryProject;
import domain.InvestigationProject;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLPreliminaryProjectController implements Initializable{

    private final PreliminaryProjectDAO PRELIMINARYPROJECT_DAO = new PreliminaryProjectDAO();
    private final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private List<String> studentsInformationList;
    private List<String> codirectorsList;
    private List<String> lgacList;
    private List<String> removeStudentsList;
    private List<String> removeCodirectorsList;
    private List<String> removeLgacList;
    private String idPreliminaryProject;
    private static String memberInformation;

    @FXML
    private JFXComboBox<String> comboBoxPreliminaryProjectStatus;
    @FXML
    private JFXListView<String> listViewCodirectors;
    @FXML
    private JFXTextArea textAreaCodirectorsEmail;
    @FXML
    private JFXTextArea textAreaCodirectorsName;
    @FXML
    private JFXTextArea textAreaPreliminaryProjectDescription;
    @FXML
    private JFXTextArea textAreaPreliminaryProjectTitle;
    @FXML
    private DatePicker datePickerPreliminaryProjectStarDate;
    @FXML
    private JFXTextArea textAreaPreliminaryProjectDuration;
    @FXML
    private JFXTextArea textAreaPreliminaryProjectModality;
    @FXML
    private JFXTextArea textAreaLgac;
    @FXML
    private JFXListView<String> listViewLgac;
    @FXML
    private JFXTextArea textAreaStudentName;
    @FXML
    private JFXTextArea textAreaStudentEmail;
    @FXML
    private JFXListView<String> listViewPreliminaryProjectStudents;
    @FXML
    private JFXComboBox<String> comboBoxAssociatedProject;
    @FXML
    private Label labelAnchorPaneTitle;
    @FXML
    private Label labelMemberInformation;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private JFXButton buttonRemoveCodirector;
    @FXML
    private JFXButton buttonRemoveLgac;
    @FXML
    private JFXButton buttonRemoveStudent;
    @FXML
    private JFXButton buttonSaveModifyPreliminaryProject;
    @FXML
    private JFXButton buttonExitModifyPreliminaryProject;

    public void setIdPreliminaryProject(String idPreliminaryProject) {
        this.idPreliminaryProject = idPreliminaryProject;
    }

    public void setIdInvestigationProject(String idInvestigationProject){
        try {
            InvestigationProject investigationProject = INVESTIGATIONPROJECT_DAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProject);
            String investigationProjectInformation = idInvestigationProject + " [" + investigationProject.getTitle() + "]";
            comboBoxAssociatedProject.setValue(investigationProjectInformation);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        validateFields();
        initializeList();
        setComboBoxWithInvestigationProject();
        setActualDate();
        setComboBoxPreliminaryProjectStatus();
        comboBoxPreliminaryProjectStatus.setPromptText("Asignado");
        comboBoxPreliminaryProjectStatus.setDisable(true);
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

    public void initializePreliminaryProjectModify(){

        labelAnchorPaneTitle.setText("MODIFICACIÓN DE ANTEPROYECTO");
        datePickerPreliminaryProjectStarDate.setDisable(false);
        comboBoxPreliminaryProjectStatus.setDisable(false);
        buttonRemoveCodirector.setVisible(true);
        buttonRemoveLgac.setVisible(true);
        buttonRemoveStudent.setVisible(true);
        buttonSaveModifyPreliminaryProject.setVisible(true);
        buttonExitModifyPreliminaryProject.setVisible(true);
        setPreliminaryProjectModifyInformation(idPreliminaryProject);
        setPreliminaryProjectModifyTables();
    }

    public void setPreliminaryProjectModifyInformation(String idPreliminaryProject){

        try {
            PreliminaryProject preliminaryProject = PRELIMINARYPROJECT_DAO.foundPreliminaryProjectById(idPreliminaryProject);

            textAreaPreliminaryProjectTitle.setText(preliminaryProject.getTitle());
            textAreaPreliminaryProjectDuration.setText(String.valueOf(preliminaryProject.getDuration()));
            textAreaPreliminaryProjectModality.setText(preliminaryProject.getModality());
            textAreaPreliminaryProjectDescription.setText(preliminaryProject.getDescription());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(simpleDateFormat.format(preliminaryProject.getStartDate()));
            datePickerPreliminaryProjectStarDate.setValue(startLocalDate);

            comboBoxPreliminaryProjectStatus.setValue(preliminaryProject.getStatus());

            String idInvestigationProject = preliminaryProject.getIdInvestigationProject();
            if (idInvestigationProject != null) {
                InvestigationProject investigationProject = INVESTIGATIONPROJECT_DAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProject);
                String investigationProjectInformation = idInvestigationProject + " [" + investigationProject.getTitle() + "]";
                comboBoxAssociatedProject.setValue(investigationProjectInformation);
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void setPreliminaryProjectModifyTables(){

        try {
            List<String> studentsInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProject);
            studentsInformationList.addAll(studentsInformationRecoveredList);
            listViewPreliminaryProjectStudents.getItems().addAll(studentsInformationRecoveredList);

            List<String> codirectosInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProject);
            codirectorsList.addAll(codirectosInformationRecoveredList);
            listViewCodirectors.getItems().addAll(codirectosInformationRecoveredList);

            List<String> lgacInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundLgacByIdPreliminaryProject(idPreliminaryProject);
            lgacList.addAll(lgacInformationRecoveredList);
            listViewLgac.getItems().addAll(lgacInformationRecoveredList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void clickedRemoveCodirector(ActionEvent actionEvent){

        String codirector =  listViewCodirectors.getSelectionModel().getSelectedItem();

        if(codirector!=null){

            codirectorsList.remove(codirector);
            removeCodirectorsList.add(codirector);
            listViewCodirectors.getItems().remove(codirector);
            listViewCodirectors.refresh();
            Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
            workPlanConfirmation.setTitle("Confirmación");
            workPlanConfirmation.setHeaderText("Se ha eliminado el codirector correctamente");
            ButtonType confirmation = new ButtonType("Ok");
            workPlanConfirmation.getButtonTypes().setAll(confirmation);
            workPlanConfirmation.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningun codirector");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void clickedRemoveStudent(ActionEvent actionEvent){

        String student =  listViewPreliminaryProjectStudents.getSelectionModel().getSelectedItem();

        if(student !=null){

            studentsInformationList.remove(student);
            removeStudentsList.add(student);
            listViewPreliminaryProjectStudents.getItems().remove(student);
            listViewPreliminaryProjectStudents.refresh();
            Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
            workPlanConfirmation.setTitle("Confirmación");
            workPlanConfirmation.setHeaderText("Se ha eliminado el estudiante correctamente");
            ButtonType confirmation = new ButtonType("Ok");
            workPlanConfirmation.getButtonTypes().setAll(confirmation);
            workPlanConfirmation.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningun estudiante");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void clickedRemoveLgac(ActionEvent actionEvent){

        String lgac =  listViewLgac.getSelectionModel().getSelectedItem();

        if(lgac !=null){

            lgacList.remove(lgac);
            removeLgacList.add(lgac);
            listViewLgac.getItems().remove(lgac);
            listViewLgac.refresh();
            Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
            workPlanConfirmation.setTitle("Confirmación");
            workPlanConfirmation.setHeaderText("Se ha eliminado la LGAC correctamente");
            ButtonType confirmation = new ButtonType("Ok");
            workPlanConfirmation.getButtonTypes().setAll(confirmation);
            workPlanConfirmation.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna LGAC");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }


    public void validateFields(){

        final String NAME_PATTERN = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        final String TEXT_PATTERN = "^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[0-9\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*(\\.[A-Za-z]{2,})$";

        final String NUMBER_PATTERN = "^\\d+$";

        final StringLengthValidator LENGTH_VALIDATOR = new StringLengthValidator(45);
        LENGTH_VALIDATOR.setMessage("Sobrepasó los 45 caracteres");

        final StringLengthValidator LENGTH_TITLE_VALIDATOR = new StringLengthValidator(100);
        LENGTH_TITLE_VALIDATOR.setMessage("Sobrepasó los 100 caracteres");

        final StringLengthValidator LENGTH_DESCRIPTION_VALIDATOR = new StringLengthValidator(300);
        LENGTH_DESCRIPTION_VALIDATOR.setMessage("Sobrepasó los 300 caracteres");

        final StringLengthValidator LENGTH_EMAIL_VALIDATOR = new StringLengthValidator(320);
        LENGTH_EMAIL_VALIDATOR.setMessage("Sobrepasó los 320 caracteres");

        final RegexValidator NUMBER_VALIDATOR = new RegexValidator();
        NUMBER_VALIDATOR.setRegexPattern(NUMBER_PATTERN);
        NUMBER_VALIDATOR.setMessage("Solo números enteros admitidos");

        final RequiredFieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();
        REQUIRED_VALIDATOR.setMessage("Campo obligatorio");

        final RegexValidator TEXT_VALIDATOR = new RegexValidator();
        TEXT_VALIDATOR.setRegexPattern(TEXT_PATTERN);
        TEXT_VALIDATOR.setMessage("Campo inválido");

        final RegexValidator NAME_VALIDATOR = new RegexValidator();
        NAME_VALIDATOR.setRegexPattern(NAME_PATTERN);
        NAME_VALIDATOR.setMessage("Campo inválido");

        final RegexValidator EMAIL_VALIDATOR = new RegexValidator();
        EMAIL_VALIDATOR.setRegexPattern(EMAIL_PATTERN);
        EMAIL_VALIDATOR.setMessage("Formato de correo inválido");

        textAreaPreliminaryProjectTitle.getValidators().add(REQUIRED_VALIDATOR);
        textAreaPreliminaryProjectDuration.getValidators().add(REQUIRED_VALIDATOR);
        textAreaPreliminaryProjectModality.getValidators().add(REQUIRED_VALIDATOR);
        textAreaPreliminaryProjectDescription.getValidators().add(REQUIRED_VALIDATOR);

        textAreaPreliminaryProjectTitle.getValidators().add(TEXT_VALIDATOR);
        textAreaPreliminaryProjectModality.getValidators().add(NAME_VALIDATOR);
        textAreaPreliminaryProjectDescription.getValidators().add(TEXT_VALIDATOR);

        textAreaPreliminaryProjectDuration.getValidators().add(NUMBER_VALIDATOR);

        textAreaStudentName.getValidators().add(NAME_VALIDATOR);
        textAreaCodirectorsName.getValidators().add(NAME_VALIDATOR);
        textAreaLgac.getValidators().add(TEXT_VALIDATOR);

        textAreaCodirectorsEmail.getValidators().add(EMAIL_VALIDATOR);
        textAreaStudentEmail.getValidators().add(EMAIL_VALIDATOR);

        textAreaPreliminaryProjectTitle.getValidators().add(LENGTH_TITLE_VALIDATOR);
        textAreaStudentName.getValidators().add(LENGTH_TITLE_VALIDATOR);
        textAreaCodirectorsName.getValidators().add(LENGTH_TITLE_VALIDATOR);

        textAreaPreliminaryProjectModality.getValidators().add(LENGTH_VALIDATOR);

        textAreaPreliminaryProjectDescription.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textAreaLgac.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);

        textAreaCodirectorsEmail.getValidators().add(LENGTH_EMAIL_VALIDATOR);
        textAreaStudentEmail.getValidators().add(LENGTH_EMAIL_VALIDATOR);

    }

    public void initializeList(){

        studentsInformationList = new ArrayList<>();
        codirectorsList = new ArrayList<>();
        lgacList = new ArrayList<>();

        removeStudentsList = new ArrayList<>();
        removeCodirectorsList = new ArrayList<>();
        removeLgacList = new ArrayList<>();
    }

    public void setComboBoxWithInvestigationProject(){

        try {
            ObservableList<String> investigationProjectsObservableList = FXCollections.observableArrayList();
            List<InvestigationProject> investigationProjectList = INVESTIGATIONPROJECT_DAO.displayAllinvestigationProjects();

            for (InvestigationProject investigationProject : investigationProjectList) {
                String comboBoxIdInvestigationProject = investigationProject.getIdInvestigationProject();
                String investigationProjectTitle = investigationProject.getTitle();
                String comboBoxInvestigationProject = comboBoxIdInvestigationProject + " [" + investigationProjectTitle + "]";
                investigationProjectsObservableList.add(comboBoxInvestigationProject);
            }

            comboBoxAssociatedProject.setItems(investigationProjectsObservableList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void setComboBoxPreliminaryProjectStatus(){

        ObservableList<String> preliminaryProjectStatusObservableList = FXCollections.observableArrayList();
        List<String> preliminaryProjectStatus = new ArrayList<>();
        preliminaryProjectStatus.add("Avalado");
        preliminaryProjectStatus.add("Asignado");
        preliminaryProjectStatus.add("Terminado");
        preliminaryProjectStatus.add("Vigente");
        preliminaryProjectStatusObservableList.addAll(preliminaryProjectStatus);
        comboBoxPreliminaryProjectStatus.setItems(preliminaryProjectStatusObservableList);

    }

    public void setActualDate(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date actualDate = new Date();
        LocalDate actualLocalDate = LocalDate.parse(dateFormat.format(actualDate));
        datePickerPreliminaryProjectStarDate.setValue(actualLocalDate);
        datePickerPreliminaryProjectStarDate.setDisable(true);
    }

    public void clickedAddCodirectorEmail(ActionEvent actionEvent){

        boolean correctCodirectorName = textAreaCodirectorsName.validate();
        boolean correctCodirectorEmail = textAreaCodirectorsEmail.validate();

        if(correctCodirectorEmail && correctCodirectorName){

            String codirectorEmail = textAreaCodirectorsEmail.getText().trim();
            String codirectorName = textAreaCodirectorsName.getText().trim();
            String codirectorInformation = codirectorName + " [" + codirectorEmail + "]";

            boolean codirectorExist = false;
            for(String codirector : codirectorsList){

                String[] codirectorRecoveredName = codirector.split("\\[");
                String[] codirectorRecoveredEmail = codirectorRecoveredName[1].split("\\]");

                if(codirectorRecoveredEmail[0].equals(codirectorEmail)){
                    codirectorExist = true;
                    break;
                }
            }

            if(!codirectorExist){


                for(String codirectorInformationRemove : removeCodirectorsList){
                    if(codirectorInformationRemove.equals(codirectorInformation)){
                        removeCodirectorsList.remove(codirectorInformation);
                    }
                }

                codirectorsList.add(codirectorInformation);
                listViewCodirectors.getItems().add(codirectorInformation);
                textAreaCodirectorsEmail.clear();
                textAreaCodirectorsName.clear();

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Ya existe un codirector con ese correo");
                alert.setContentText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }
        }
    }

    public void clickedAddLgac(ActionEvent actionEvent){

        boolean correctLgac = textAreaLgac.validate();

        if(correctLgac){

            String lgac = textAreaLgac.getText().trim();

            boolean lgacExist = false;
            for(String associatedLgac : lgacList){
                if(lgac.equals(associatedLgac)){
                    lgacExist = true;
                    break;
                }
            }

            if(!lgacExist){


                for(String lgacRemove : removeLgacList){
                    if(lgacRemove.equals(lgac)){
                        removeLgacList.remove(lgac);
                    }
                }

                lgacList.add(lgac);
                listViewLgac.getItems().add(lgac);
                textAreaLgac.clear();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Ya se agregó esa LGAC");
                alert.setContentText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }

        }
    }

    public void clickedAddStudent(ActionEvent actionEvent) {

        boolean correctStudentName = textAreaStudentName.validate();
        boolean correctStudentEmail = textAreaStudentEmail.validate();
        int maxOfStudents = 2;

        if (correctStudentName && correctStudentEmail) {

            if(studentsInformationList.size() < maxOfStudents){

                String studentName = textAreaStudentName.getText().trim();
                String studentEmail = textAreaStudentEmail.getText().trim();
                String studentInformation = studentName + " [" + studentEmail + "]";

                boolean studentExist = false;
                for (String student : studentsInformationList) {

                    String[] studentRecoveredName = student.split("\\[");
                    String[] studentRecoveredEmail = studentRecoveredName[1].split("\\]");

                    if (studentRecoveredEmail[0].equals(studentEmail)) {
                        studentExist = true;
                        break;
                    }
                }

                if (!studentExist) {

                    for(String studentInformationRemove : removeStudentsList){
                        if(studentInformationRemove.equals(studentInformation)){
                            removeStudentsList.remove(studentInformation);
                        }
                    }

                    studentsInformationList.add(studentInformation);
                    listViewPreliminaryProjectStudents.getItems().add(studentInformation);
                    textAreaStudentName.clear();
                    textAreaStudentEmail.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Ya existe un estudiante con el mismo correo");
                    alert.setContentText(null);
                    alert.setTitle("Error");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Solo puede haber 2 estudiantes como máximo");
                alert.setContentText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }

        }
    }

    public void clickedSavePreliminaryProject(ActionEvent actionEvent){

        Date actualDate = new Date();
        boolean correctTitle = textAreaPreliminaryProjectTitle.validate();
        boolean correctDuration = textAreaPreliminaryProjectDuration.validate();
        boolean correctModality = textAreaPreliminaryProjectModality.validate();
        boolean correctDescription = textAreaPreliminaryProjectDescription.validate();

        if((!codirectorsList.isEmpty()) && (!studentsInformationList.isEmpty()) && (!lgacList.isEmpty())) {
            if(correctTitle && correctDuration && correctDescription && correctModality) {

                try {
                    int duration = Integer.parseInt(textAreaPreliminaryProjectDuration.getText().trim());
                    int minMonths = 1;
                    int maxMonths = 48;

                    if (minMonths <= duration && duration <= maxMonths) {

                        String title = textAreaPreliminaryProjectTitle.getText().trim();
                        String modality = textAreaPreliminaryProjectModality.getText().trim();
                        String description = textAreaPreliminaryProjectDescription.getText().trim();
                        String investigationProject = null;
                        String investigationProjectInformation = comboBoxAssociatedProject.getSelectionModel().getSelectedItem();

                        if (investigationProjectInformation != null) {
                            String[] idInvestigationProjectArray = investigationProjectInformation.split("\\[");
                            investigationProject = idInvestigationProjectArray[0].trim();
                        }

                        boolean uniquePreliminaryProject = true;

                        List<PreliminaryProject> preliminaryProjectsList = PRELIMINARYPROJECT_DAO.displayAllPreliminaryProjects();

                        if (investigationProject == null) {
                            for (PreliminaryProject preliminaryProject : preliminaryProjectsList) {
                                if (preliminaryProject.getTitle().equals(title) &&
                                        (preliminaryProject.getDuration() == duration) &&
                                        preliminaryProject.getModality().equals(modality) &&
                                        preliminaryProject.getDescription().equals(description) &&
                                        preliminaryProject.getStartDate().equals(actualDate)) {
                                    uniquePreliminaryProject = false;
                                    break;
                                }
                            }
                        } else {
                            for (PreliminaryProject preliminaryProject : preliminaryProjectsList) {
                                if (preliminaryProject.getTitle().equals(title) &&
                                        (preliminaryProject.getDuration() == duration) &&
                                        preliminaryProject.getModality().equals(modality) &&
                                        preliminaryProject.getDescription().equals(description) &&
                                        preliminaryProject.getStartDate().equals(actualDate) &&
                                        preliminaryProject.getIdInvestigationProject().equals(investigationProject)) {
                                    uniquePreliminaryProject = false;
                                    break;
                                }
                            }
                        }

                        if (uniquePreliminaryProject) {

                            String defaultStatus = "Asignado";
                            boolean preliminaryProjectAddedResult;

                            if (investigationProject == null) {

                                PreliminaryProject preliminaryProject = new PreliminaryProject(title, description, actualDate, defaultStatus, modality, duration);
                                preliminaryProjectAddedResult = PRELIMINARYPROJECT_DAO.addPreliminaryProject(preliminaryProject);

                            } else {
                                PreliminaryProject preliminaryProject = new PreliminaryProject(title, description, actualDate,
                                        defaultStatus, modality, duration, investigationProject);
                                preliminaryProjectAddedResult = PRELIMINARYPROJECT_DAO.addPreliminaryProject(preliminaryProject);
                            }

                            if (preliminaryProjectAddedResult) {

                                String idPreliminaryProject = "PLP-" + (PRELIMINARYPROJECT_DAO.getLastIdNumber("idPreliminaryProject", "preliminaryproject") - 1);

                                for (String lgac : lgacList) {
                                    PRELIMINARYPROJECT_DAO.addLgacToPreliminaryProject(idPreliminaryProject, lgac);
                                }

                                for (String studentInformation : studentsInformationList) {
                                    PRELIMINARYPROJECT_DAO.addStudentInformationToPreliminaryProject(idPreliminaryProject, studentInformation);
                                }

                                for (String codirectorInformation : codirectorsList) {
                                    PRELIMINARYPROJECT_DAO.addCodirectorToPreliminaryProject(idPreliminaryProject, codirectorInformation);
                                }

                                Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                                workPlanConfirmation.setTitle("Confirmación");
                                workPlanConfirmation.setHeaderText("Anteproyecto registrado con éxito");
                                ButtonType confirmation = new ButtonType("Ok");
                                workPlanConfirmation.getButtonTypes().setAll(confirmation);
                                workPlanConfirmation.showAndWait();

                                clearFields();
                                exitPreliminaryProject();

                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Base de datos no disponible por el momento");
                                alert.setContentText(null);
                                alert.setTitle("Error en conexión");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Ya existe un anteproyecto registrado con esa información");
                            alert.setContentText(null);
                            alert.setTitle("Registro duplicado");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Duración de 1 a 48 meses");
                        alert.setContentText(null);
                        alert.setTitle("Campos inválidos");
                        alert.showAndWait();
                    }

                }catch(BusinessLogicException ex){
                    ALERT_INTERFACE.openAlertFailedInsert();
                }

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Favor de corregir los campos inválidos");
                alert.setContentText(null);
                alert.setTitle("Campos inválidos");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Anteproyecto sin codirectores o sin LGAC o sin estudiantes");
            alert.setContentText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }

    }

    public boolean validateChanges(){

        boolean changesInPreliminaryProject = true;
        try {
            PreliminaryProject oldPreliminaryProject = PRELIMINARYPROJECT_DAO.foundPreliminaryProjectById(idPreliminaryProject);

            String title = textAreaPreliminaryProjectTitle.getText().trim();
            String modality = textAreaPreliminaryProjectModality.getText().trim();
            String description = textAreaPreliminaryProjectDescription.getText().trim();
            int duration = Integer.parseInt(textAreaPreliminaryProjectDuration.getText().trim());
            String investigationProject = null;
            String investigationProjectInformation = comboBoxAssociatedProject.getSelectionModel().getSelectedItem();
            Date startDate = Date.from(datePickerPreliminaryProjectStarDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String status = comboBoxPreliminaryProjectStatus.getValue();

            if (investigationProjectInformation != null) {
                String[] idInvestigationProjectArray = investigationProjectInformation.split("\\[");
                investigationProject = idInvestigationProjectArray[0].trim();
            }

            if (investigationProjectInformation != null) {
                if (oldPreliminaryProject.getTitle().equals(title) &&
                        oldPreliminaryProject.getModality().equals(modality) &&
                        oldPreliminaryProject.getDescription().equals(description) &&
                        oldPreliminaryProject.getDuration() == duration &&
                        oldPreliminaryProject.getStartDate().equals(startDate) &&
                        oldPreliminaryProject.getStatus().equals(status)) {

                    if (oldPreliminaryProject.getIdInvestigationProject() != null) {
                        if (oldPreliminaryProject.getIdInvestigationProject().equals(investigationProject)) {
                            changesInPreliminaryProject = false;
                        }
                    }
                }
            } else {
                if (oldPreliminaryProject.getTitle().equals(title) &&
                        oldPreliminaryProject.getModality().equals(modality) &&
                        oldPreliminaryProject.getDescription().equals(description) &&
                        oldPreliminaryProject.getDuration() == duration &&
                        oldPreliminaryProject.getStartDate().equals(startDate) &&
                        oldPreliminaryProject.getStatus().equals(status)) {
                    changesInPreliminaryProject = false;
                }
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

        return changesInPreliminaryProject;
    }

    public boolean validateStartDate(Date startDate){

        boolean correctStarDate=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date actualDate = new Date();
        try {
            Date actualDateSimpleFormat = dateFormat.parse(dateFormat.format(actualDate));

            if (actualDateSimpleFormat.before(startDate) || actualDateSimpleFormat.equals(startDate)) {
                correctStarDate = true;
            }
        }catch (ParseException parseException){
            Logger.getLogger(FXMLPreliminaryProjectController.class.getName()).log(Level.SEVERE, null, parseException);
        }
        return correctStarDate;
    }


    public void saveModifyPreliminaryProject(ActionEvent actionEvent){

        boolean correctTitle = textAreaPreliminaryProjectTitle.validate();
        boolean correctDuration = textAreaPreliminaryProjectDuration.validate();
        boolean correctModality = textAreaPreliminaryProjectModality.validate();
        boolean correctDescription = textAreaPreliminaryProjectDescription.validate();
        Date startDate = Date.from(datePickerPreliminaryProjectStarDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        boolean correctStartDate = validateStartDate(startDate);


        if((!codirectorsList.isEmpty()) && (!studentsInformationList.isEmpty()) && (!lgacList.isEmpty())) {


            try {
                PreliminaryProject preliminaryProjectValidateDates = PRELIMINARYPROJECT_DAO.foundPreliminaryProjectById(idPreliminaryProject);

                if (preliminaryProjectValidateDates != null) {
                    if (startDate.equals(preliminaryProjectValidateDates.getStartDate())) {
                        correctStartDate = true;
                    }
                }

                if (correctTitle && correctDuration && correctDescription && correctModality) {

                    if (correctStartDate) {

                        int duration = Integer.parseInt(textAreaPreliminaryProjectDuration.getText().trim());
                        int minMonths = 1;
                        int maxMonths = 48;

                        if (minMonths <= duration && duration <= maxMonths) {

                            String title = textAreaPreliminaryProjectTitle.getText().trim();
                            String modality = textAreaPreliminaryProjectModality.getText().trim();
                            String description = textAreaPreliminaryProjectDescription.getText().trim();
                            String investigationProject = null;
                            String investigationProjectInformation = comboBoxAssociatedProject.getSelectionModel().getSelectedItem();
                            String status = comboBoxPreliminaryProjectStatus.getSelectionModel().getSelectedItem();

                            if (investigationProjectInformation != null) {
                                String[] idInvestigationProjectArray = investigationProjectInformation.split("\\[");
                                investigationProject = idInvestigationProjectArray[0].trim();
                            }

                            boolean uniquePreliminaryProject = true;

                            List<PreliminaryProject> preliminaryProjectsList = PRELIMINARYPROJECT_DAO.displayAllPreliminaryProjects();

                            if (investigationProject == null) {
                                for (PreliminaryProject preliminaryProject : preliminaryProjectsList) {
                                    if (preliminaryProject.getTitle().equals(title) &&
                                            (preliminaryProject.getDuration() == duration) &&
                                            preliminaryProject.getModality().equals(modality) &&
                                            preliminaryProject.getDescription().equals(description) &&
                                            preliminaryProject.getStartDate().equals(startDate) &&
                                            preliminaryProject.getStatus().equals(status)) {
                                        uniquePreliminaryProject = false;
                                        break;
                                    }
                                }
                            } else {
                                for (PreliminaryProject preliminaryProject : preliminaryProjectsList) {
                                    if (preliminaryProject.getTitle().equals(title) &&
                                            (preliminaryProject.getDuration() == duration) &&
                                            preliminaryProject.getModality().equals(modality) &&
                                            preliminaryProject.getDescription().equals(description) &&
                                            preliminaryProject.getStartDate().equals(startDate) &&
                                            preliminaryProject.getStatus().equals(status)) {
                                        if (preliminaryProject.getIdInvestigationProject() != null) {
                                            if (preliminaryProject.getIdInvestigationProject().equals(investigationProject)) {
                                                uniquePreliminaryProject = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            if (uniquePreliminaryProject || !validateChanges()) {

                                boolean preliminaryProjectAddedResult;

                                if (investigationProject == null) {

                                    PreliminaryProject preliminaryProject = new PreliminaryProject(title, description, startDate, status, modality, duration);
                                    preliminaryProject.setIdPreliminaryProyect(idPreliminaryProject);
                                    preliminaryProjectAddedResult = PRELIMINARYPROJECT_DAO.updatePreliminaryProject(preliminaryProject);

                                } else {
                                    PreliminaryProject preliminaryProject = new PreliminaryProject(title, description, startDate,
                                            status, modality, duration, investigationProject);
                                    preliminaryProject.setIdPreliminaryProyect(idPreliminaryProject);
                                    preliminaryProjectAddedResult = PRELIMINARYPROJECT_DAO.updatePreliminaryProject(preliminaryProject);
                                }

                                if (preliminaryProjectAddedResult) {

                                    removePreliminaryProjectOldObjects();
                                    addNewPreliminaryProjectObject();

                                    Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                                    workPlanConfirmation.setTitle("Confirmación");
                                    workPlanConfirmation.setHeaderText("Anteproyecto actualizado con éxito");
                                    ButtonType confirmation = new ButtonType("Ok");
                                    workPlanConfirmation.getButtonTypes().setAll(confirmation);
                                    workPlanConfirmation.showAndWait();

                                    clearFields();
                                    exitPreliminaryProject();

                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Base de datos no disponible por el momento");
                                    alert.setContentText(null);
                                    alert.setTitle("Error en conexión");
                                    alert.showAndWait();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Ya existe un anteproyecto registrado con esa información");
                                alert.setContentText(null);
                                alert.setTitle("Registro duplicado");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Duración de 1 a 48 meses");
                            alert.setContentText(null);
                            alert.setTitle("Campos inválidos");
                            alert.showAndWait();
                        }

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Favor de corregir fecha inválida");
                        alert.setContentText(null);
                        alert.setTitle("Campos inválidos");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Favor de corregir los campos inválidos");
                    alert.setContentText(null);
                    alert.setTitle("Campos inválidos");
                    alert.showAndWait();
                }

            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Anteproyecto sin codirectores o sin LGAC o sin estudiantes");
            alert.setContentText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void removePreliminaryProjectOldObjects(){

        try {
            List<String> recoveredStudentsList = PRELIMINARYPROJECT_DAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProject);
            for (String removeStudent : removeStudentsList) {
                boolean existStudent = false;
                for (String recoveredStudent : recoveredStudentsList) {
                    if (recoveredStudent.equals(removeStudent)) {
                        existStudent = true;
                        break;
                    }
                }
                if (existStudent) {
                    PRELIMINARYPROJECT_DAO.deletePreliminaryProjectStudent(idPreliminaryProject, removeStudent);
                }
            }

            List<String> recoveredCodirectorsList = PRELIMINARYPROJECT_DAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProject);
            for (String removeCodirector : removeCodirectorsList) {
                boolean existCodirector = false;
                for (String recoveredCodirector : recoveredCodirectorsList) {
                    if (recoveredCodirector.equals(removeCodirector)) {
                        existCodirector = true;
                        break;
                    }
                }
                if (existCodirector) {
                    PRELIMINARYPROJECT_DAO.deletePreliminaryProjectCodirector(idPreliminaryProject, removeCodirector);
                }
            }

            List<String> recoveredLgacList = PRELIMINARYPROJECT_DAO.foundLgacByIdPreliminaryProject(idPreliminaryProject);
            for (String removeLgac : removeLgacList) {
                boolean existLgac = false;
                for (String recoveredLgac : recoveredLgacList) {
                    if (recoveredLgac.equals(removeLgac)) {
                        existLgac = true;
                        break;
                    }
                }
                if (existLgac) {
                    PRELIMINARYPROJECT_DAO.deletePreliminaryProjectLgac(idPreliminaryProject, removeLgac);
                }
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void addNewPreliminaryProjectObject(){

        try {
            List<String> recoveredStudentsList = PRELIMINARYPROJECT_DAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProject);
            for (String studentInformation : studentsInformationList) {
                boolean existStudent = false;
                for (String recoveredStudent : recoveredStudentsList) {
                    if (recoveredStudent.equals(studentInformation)) {
                        existStudent = true;
                        break;
                    }
                }
                if (!existStudent) {
                    PRELIMINARYPROJECT_DAO.addStudentInformationToPreliminaryProject(idPreliminaryProject, studentInformation);
                }
            }

            List<String> recoveredCodirectorsList = PRELIMINARYPROJECT_DAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProject);
            for (String codirectorInformation : codirectorsList) {
                boolean existCodirector = false;
                for (String recoveredCodirector : recoveredCodirectorsList) {
                    if (recoveredCodirector.equals(codirectorInformation)) {
                        existCodirector = true;
                        break;
                    }
                }
                if (!existCodirector) {
                    PRELIMINARYPROJECT_DAO.addCodirectorToPreliminaryProject(idPreliminaryProject, codirectorInformation);
                }
            }

            List<String> recoveredLgacList = PRELIMINARYPROJECT_DAO.foundLgacByIdPreliminaryProject(idPreliminaryProject);
            for (String lgac : lgacList) {
                boolean existLgac = false;
                for (String recoveredLgac : recoveredLgacList) {
                    if (recoveredLgac.equals(lgac)) {
                        existLgac = true;
                        break;
                    }
                }
                if (!existLgac) {
                    PRELIMINARYPROJECT_DAO.addLgacToPreliminaryProject(idPreliminaryProject, lgac);
                }
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }


    public void clearFields(){
        textAreaPreliminaryProjectTitle.clear();
        textAreaPreliminaryProjectDuration.clear();
        textAreaPreliminaryProjectModality.clear();
        textAreaPreliminaryProjectDescription.clear();
        listViewPreliminaryProjectStudents.getItems().clear();
        listViewLgac.getItems().clear();
        listViewCodirectors.getItems().clear();
        studentsInformationList.clear();
        lgacList.clear();
        codirectorsList.clear();
        textAreaCodirectorsName.clear();
        textAreaCodirectorsEmail.clear();
        textAreaStudentName.clear();
        textAreaStudentEmail.clear();
        textAreaLgac.clear();
    }

    public void clickedExitAddPreliminaryProject(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar el registro del anteproyecto?");
        alert.setContentText("No se realizará ningún cambio en el sistema");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                try {

                    buttonRemoveCodirector.setVisible(false);
                    buttonRemoveLgac.setVisible(false);
                    buttonRemoveStudent.setVisible(false);
                    clearFields();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProjectList.fxml"));
                    Parent root = loader.load();
                    FXMLPreliminaryProjectListController controller = loader.getController();
                    controller.setMemberInformation(labelMemberInformation.getText());
                    controller.restrictAccess(getCurpMember());


                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("SGP-CA");
                    stage.setScene(scene);
                    stage.show();

                    Stage stageReturn = (Stage) anchorPaneMain.getScene().getWindow();
                    stageReturn.close();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLPreliminaryProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void clickedExitModifyPreliminaryProject(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar la modificación del anteproyecto?");
        alert.setContentText("No se realizará ningún cambio en el sistema");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                try {

                    buttonRemoveCodirector.setVisible(false);
                    buttonRemoveLgac.setVisible(false);
                    buttonRemoveStudent.setVisible(false);
                    buttonSaveModifyPreliminaryProject.setVisible(false);
                    buttonExitModifyPreliminaryProject.setVisible(false);
                    clearFields();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProjectList.fxml"));
                    Parent root = loader.load();
                    FXMLPreliminaryProjectListController controller = loader.getController();
                    controller.setMemberInformation(labelMemberInformation.getText());
                    controller.restrictAccess(getCurpMember());


                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("SGP-CA");
                    stage.setScene(scene);
                    stage.show();

                    Stage stageReturn = (Stage) anchorPaneMain.getScene().getWindow();
                    stageReturn.close();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLPreliminaryProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void exitPreliminaryProject(){
        try{

            buttonRemoveCodirector.setVisible(false);
            buttonRemoveLgac.setVisible(false);
            buttonRemoveStudent.setVisible(false);
            buttonSaveModifyPreliminaryProject.setVisible(false);
            buttonExitModifyPreliminaryProject.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLPreliminaryProjectList.fxml"));
            Parent root = loader.load();
            FXMLPreliminaryProjectListController controller = loader.getController();
            controller.setMemberInformation(labelMemberInformation.getText());
            controller.restrictAccess(getCurpMember());


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("SGP-CA");
            stage.setScene(scene);
            stage.show();

            Stage stageReturn = (Stage) anchorPaneMain.getScene().getWindow();
            stageReturn.close();

        }catch(IOException ex){
            Logger.getLogger(FXMLPreliminaryProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
