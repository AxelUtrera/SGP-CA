package controller;

import businesslogic.BusinessLogicException;
import businesslogic.EventAssistantDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.EventAssistant;
import domain.EventConstancy;
import domain.Member;
import javafx.fxml.FXML;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLEventConstancyController implements Initializable{

    private final EventAssistantDAO EVENTASSISTANT_DAO = new EventAssistantDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private EventConstancy eventConstancySelected;
    private static int numberOfConstancyDownload = 1;
    private String idAcademicEvent;
    private ObservableList<EventConstancy> eventConstancyObservableList;
    private List<Member> validatorsList;
    private List<String> regulatoryNotesList;
    private static String memberInformation;

    @FXML
    private TableView<EventConstancy> tableViewEventConstancy;
    @FXML
    private TableColumn<EventConstancy, String> tableColumnRecognitionType;
    @FXML
    private TableColumn<EventConstancy, String> tableColumnAssistantName;
    @FXML
    private JFXListView<String> listViewValidatorsName;
    @FXML
    private JFXListView<String> listViewRegulatoryNotes;
    @FXML
    private JFXListView<String> listViewNewValidatorsMail;
    @FXML
    private JFXListView<String> listViewNewRegulatoryNotes;
    @FXML
    private JFXButton buttonExitConsultConstancies;
    @FXML
    private JFXTextArea textAreaReceiverName;
    @FXML
    private JFXTextArea textAreaRecognitionType;
    @FXML
    private JFXTextArea textAreaDescription;
    @FXML
    private AnchorPane anchorPaneConsultEventConstancy;
    @FXML
    private AnchorPane anchorPaneGenerateConstancy;
    @FXML
    private JFXTextField textFieldNewRecognitionType;
    @FXML
    private JFXTextField textFieldNewReceiverName;
    @FXML
    private JFXTextArea textAreaNewDescription;
    @FXML
    private JFXTextField textFieldNewEmailAssistant;
    @FXML
    private JFXTextField textFieldValidatorMail;
    @FXML
    private JFXTextArea textAreaRegulatoryNotes;
    @FXML
    private Label labelMemberInformation;

    public void setIdAcademicEvent(String idAcademicEvent){
        this.idAcademicEvent = idAcademicEvent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        initializeEventConstanciesTable();
        clickedEventConstancy();
        validateText();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
        }
    }

    public void setMemberInformation(String memberLoginInformation){

        memberInformation = memberLoginInformation;
        labelMemberInformation.setText(memberLoginInformation);
    }

    public void initializeEventConstanciesTable(){

        eventConstancyObservableList = FXCollections.observableArrayList();
        tableColumnRecognitionType.setCellValueFactory(new PropertyValueFactory<>("recognitionType"));
        tableColumnAssistantName.setCellValueFactory(new PropertyValueFactory<>("emailAssistant"));

        validatorsList = new ArrayList<>();
        regulatoryNotesList = new ArrayList<>();
    }

    public void validateText(){

        final String TEXT_PATTERN = "^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[0-9\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*(\\.[A-Za-z]{2,})$";

        final StringLengthValidator LENGTH_VALIDATOR = new StringLengthValidator(45);
        LENGTH_VALIDATOR.setMessage("Sobrepasó los 45 caracteres");

        final StringLengthValidator LENGTH_TITLE_VALIDATOR = new StringLengthValidator(100);
        LENGTH_TITLE_VALIDATOR.setMessage("Sobrepasó los 100 caracteres");

        final StringLengthValidator LENGTH_DESCRIPTION_VALIDATOR = new StringLengthValidator(300);
        LENGTH_DESCRIPTION_VALIDATOR.setMessage("Sobrepasó los 300 caracteres");

        final StringLengthValidator LENGTH_EMAIL_VALIDATOR = new StringLengthValidator(320);
        LENGTH_EMAIL_VALIDATOR.setMessage("Sobrepasó los 320 caracteres");

        final RequiredFieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();
        REQUIRED_VALIDATOR.setMessage("Campo obligatorio");

        final RegexValidator TEXT_VALIDATOR = new RegexValidator();
        TEXT_VALIDATOR.setRegexPattern(TEXT_PATTERN);
        TEXT_VALIDATOR.setMessage("Campo inválido");

        final RegexValidator EMAIL_VALIDATOR = new RegexValidator();
        EMAIL_VALIDATOR.setRegexPattern(EMAIL_PATTERN);
        EMAIL_VALIDATOR.setMessage("Formato de correo inválido");

        textFieldNewRecognitionType.getValidators().add(REQUIRED_VALIDATOR);
        textFieldNewReceiverName.getValidators().add(REQUIRED_VALIDATOR);
        textAreaNewDescription.getValidators().add(REQUIRED_VALIDATOR);
        textFieldNewEmailAssistant.getValidators().add(REQUIRED_VALIDATOR);
        textFieldValidatorMail.getValidators().add(REQUIRED_VALIDATOR);
        textAreaRegulatoryNotes.getValidators().add(REQUIRED_VALIDATOR);

        textFieldNewRecognitionType.getValidators().add(TEXT_VALIDATOR);
        textFieldNewReceiverName.getValidators().add(TEXT_VALIDATOR);
        textAreaNewDescription.getValidators().add(TEXT_VALIDATOR);
        textAreaRegulatoryNotes.getValidators().add(TEXT_VALIDATOR);

        textFieldNewEmailAssistant.getValidators().add(EMAIL_VALIDATOR);
        textFieldValidatorMail.getValidators().add(EMAIL_VALIDATOR);

        textFieldNewRecognitionType.getValidators().add(LENGTH_VALIDATOR);
        textFieldNewReceiverName.getValidators().add(LENGTH_TITLE_VALIDATOR);
        textAreaNewDescription.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textFieldNewEmailAssistant.getValidators().add(LENGTH_EMAIL_VALIDATOR);
        textFieldValidatorMail.getValidators().add(LENGTH_EMAIL_VALIDATOR);
        textAreaRegulatoryNotes.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
    }

    public void setEventConstancies(String idAcademicEvent){

        try {
            List<EventConstancy> eventConstancyList = EVENTASSISTANT_DAO.displayAllEventConstanciesByIdAcademicEvent(idAcademicEvent);
            eventConstancyObservableList.addAll(eventConstancyList);

            for (EventConstancy eventConstancy : eventConstancyObservableList) {

                EventAssistant eventAssistant = EVENTASSISTANT_DAO.foundEventAssistantByEmail(eventConstancy.getEmailAssistant());
                eventConstancy.setEmailAssistant(eventAssistant.getName());
            }

            tableViewEventConstancy.setItems(eventConstancyObservableList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void clickedExitConsultConstancies(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir del listado de constancias?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {

                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLHistoryEvent.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setTitle("SGP-CA");
                    stage.setScene(scene);
                    stage.show();

                    Stage stageReturn = (Stage) buttonExitConsultConstancies.getScene().getWindow();
                    stageReturn.close();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void clickedEventConstancy(){

        tableViewEventConstancy.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    EventConstancy eventConstancySelected = tableViewEventConstancy.getSelectionModel().getSelectedItem();
                    EventConstancy eventConstancy = EVENTASSISTANT_DAO.foundEventConstancyByIdEventConstancy(eventConstancySelected.getIdEventConstancy());
                    consultEventConstancy(eventConstancy);
                }catch(BusinessLogicException ex){
                    ALERT_INTERFACE.openAlertFailedInsert();
                }
            }
        });
    }

    public void consultEventConstancy(EventConstancy eventConstancy){

        eventConstancySelected = eventConstancy;
        anchorPaneConsultEventConstancy.setVisible(true);
        try {
            EventAssistant eventAssistant = EVENTASSISTANT_DAO.foundEventAssistantByEmail(eventConstancy.getEmailAssistant());
            textAreaReceiverName.setText(eventAssistant.getName());
            textAreaRecognitionType.setText(eventConstancy.getRecognitionType());

            List<String> validatorsInformation = new ArrayList<>();
            List<Member> validatorsList = EVENTASSISTANT_DAO.foundValidatorsByIdEventConstancy(eventConstancy.getIdEventConstancy());
            for (Member validator : validatorsList) {
                String validatorInformation = validator.getName() + " [" + validator.getCurp() + ']';
                validatorsInformation.add(validatorInformation);
            }
            listViewValidatorsName.getItems().addAll(validatorsInformation);

            List<String> regulatoryNotes = EVENTASSISTANT_DAO.foundRegulatoryNotesByIdEventConstancy(eventConstancy.getIdEventConstancy());
            listViewRegulatoryNotes.getItems().addAll(regulatoryNotes);

            textAreaDescription.setText(eventConstancy.getDescription());
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void clickedCloseEventConstancy(MouseEvent mouseEvent){

        textAreaReceiverName.clear();
        textAreaRecognitionType.clear();
        listViewValidatorsName.getItems().clear();
        listViewRegulatoryNotes.getItems().clear();
        textAreaDescription.clear();
        anchorPaneConsultEventConstancy.setVisible(false);
        eventConstancySelected=null;
        numberOfConstancyDownload=1;
    }

    public void refreshEventConstanciesTable(MouseEvent mouseEvent){
        tableViewEventConstancy.getSelectionModel().clearSelection();
    }

    public void clickedDownloadEventConstancy(ActionEvent actionEvent){

        boolean fileExist = true;
        String route = System.getProperty("user.home");

        do {
            File file = new File(route + "/Desktop/Constancy-"+numberOfConstancyDownload+".pdf");
            if (file.exists()) {
                numberOfConstancyDownload++;
            } else {
                try {
                    EVENTASSISTANT_DAO.generatePDFEventConstancy(eventConstancySelected, numberOfConstancyDownload);
                    Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    workPlanConfirmation.setTitle("Confirmación");
                    workPlanConfirmation.setHeaderText("Se ha logrado descargar la constancia de evento correctamente");
                    ButtonType confirmation = new ButtonType("Ok");
                    workPlanConfirmation.getButtonTypes().setAll(confirmation);
                    workPlanConfirmation.showAndWait();
                    fileExist = false;
                }catch(BusinessLogicException ex){
                    ALERT_INTERFACE.openAlertFailedInsert();
                }

            }
        }while(fileExist);
    }

    public void clickedGenerateConstancy(ActionEvent actionEvent){
        anchorPaneGenerateConstancy.setVisible(true);
    }

    public void clickedExitGenerateNewConstancy(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea salir y cancelar el procceso de generar una constancia?");
        alert.setContentText("Ningún cambio será guardado");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()){
            if (action.get() == ButtonType.OK) {

                validatorsList.clear();
                regulatoryNotesList.clear();
                textFieldNewRecognitionType.clear();
                textFieldNewReceiverName.clear();
                textAreaNewDescription.clear();
                textFieldNewEmailAssistant.clear();
                listViewNewValidatorsMail.getItems().clear();
                listViewNewRegulatoryNotes.getItems().clear();
                textFieldValidatorMail.clear();
                textAreaRegulatoryNotes.clear();
                anchorPaneGenerateConstancy.setVisible(false);
            }
        }
    }

    public void clickedSaveGeneratedConstancy(ActionEvent actionEvent){

        boolean correctRecognitionType = textFieldNewRecognitionType.validate();
        boolean correctReceiverName = textFieldNewReceiverName.validate();
        boolean correctDescription = textAreaNewDescription.validate();
        boolean correctEmailAssistant = textFieldNewEmailAssistant.validate();

        if((!regulatoryNotesList.isEmpty()) && (!validatorsList.isEmpty())) {
            if (correctRecognitionType && correctReceiverName && correctDescription && correctEmailAssistant){

                String recognitionType = textFieldNewRecognitionType.getText();
                String description = textAreaNewDescription.getText();
                String emailAssistant = textFieldNewEmailAssistant.getText();
                String receiverName = textFieldNewReceiverName.getText();
                boolean uniqueConstancy = true;

                try {
                    List<EventConstancy> eventConstanciesList = EVENTASSISTANT_DAO.displayAllEventConstanciesByIdAcademicEvent(idAcademicEvent);
                    for (EventConstancy eventConstancy : eventConstanciesList) {
                        if (eventConstancy.getRecognitionType().equals(recognitionType) &&
                                eventConstancy.getEmailAssistant().equals(emailAssistant) &&
                                eventConstancy.getIdAcademicEvent().equals(idAcademicEvent)) {
                            uniqueConstancy = false;
                            break;
                        }

                    }

                    if (uniqueConstancy) {

                        EventAssistant eventAssistantExist = EVENTASSISTANT_DAO.foundEventAssistantByEmail(emailAssistant);
                        if (eventAssistantExist == null) {
                            EventAssistant eventAssistant = new EventAssistant(emailAssistant, receiverName);
                            EVENTASSISTANT_DAO.addEventAssistant(eventAssistant);
                        }

                        EventConstancy eventConstancy = new EventConstancy(recognitionType, description, emailAssistant, idAcademicEvent);
                        boolean eventConstancyAddedResult = EVENTASSISTANT_DAO.addEventConstancy(eventConstancy);

                        if (eventConstancyAddedResult) {

                            String idEventConstancy = "EVC-" + (EVENTASSISTANT_DAO.getLastIdNumber("idEventConstancy", "eventconstancy") - 1);

                            for (Member validator : validatorsList) {
                                EVENTASSISTANT_DAO.addValidatorToEventConstancy(idEventConstancy, validator.getCurp());
                            }

                            for (String regulatoryNote : regulatoryNotesList) {
                                EVENTASSISTANT_DAO.addRegulatoryNoteToEventConstancy(idEventConstancy, regulatoryNote);
                            }

                            Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                            workPlanConfirmation.setTitle("Confirmación");
                            workPlanConfirmation.setHeaderText("Constancia guardada correctamente");
                            ButtonType confirmation = new ButtonType("Ok");
                            workPlanConfirmation.getButtonTypes().setAll(confirmation);
                            workPlanConfirmation.showAndWait();

                            validatorsList.clear();
                            regulatoryNotesList.clear();
                            textFieldNewRecognitionType.clear();
                            textFieldNewReceiverName.clear();
                            textAreaNewDescription.clear();
                            textFieldNewEmailAssistant.clear();
                            listViewNewValidatorsMail.getItems().clear();
                            listViewNewRegulatoryNotes.getItems().clear();
                            textFieldValidatorMail.clear();
                            textAreaRegulatoryNotes.clear();
                            tableViewEventConstancy.getItems().clear();
                            setEventConstancies(idAcademicEvent);
                            anchorPaneGenerateConstancy.setVisible(false);

                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Base de datos no disponible por el momento");
                            alert.setContentText(null);
                            alert.setTitle("Error en conexión");
                            alert.showAndWait();
                        }


                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Ya existe una constancia registrada con esa información");
                        alert.setContentText(null);
                        alert.setTitle("Registro duplicado");
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
            alert.setHeaderText("Constancia sin validadores o sin notas regulatorias");
            alert.setContentText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }


    }

    public void clickedAddValidator(ActionEvent actionEvent){

        boolean correctValidatorMail = textFieldValidatorMail.validate();

        if(correctValidatorMail){

            try{
                String validatorMail = textFieldValidatorMail.getText();
                Member member = MEMBER_DAO.findMemberByInstitutionalMail(validatorMail);

                if (member != null) {

                    boolean validatorExist = false;
                    for (Member memberExist : validatorsList) {
                        if (memberExist.getCurp().equals(member.getCurp())) {
                            validatorExist = true;
                            break;
                        }
                    }

                    if (!validatorExist) {

                        validatorsList.add(member);
                        String newValidator = member.getName() + " [" + member.getCurp() + ']';
                        listViewNewValidatorsMail.getItems().add(newValidator);
                        textFieldValidatorMail.clear();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Ya se agregó ese validador");
                        alert.setContentText(null);
                        alert.setTitle("Error");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("No existe un validador con ese correo institucional");
                    alert.setContentText(null);
                    alert.setTitle("Error");
                    alert.showAndWait();
                }
            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }
        }
    }

    public void clickedAddRegulatoryNote(ActionEvent actionEvent){

        boolean correctRegulatoryNote = textAreaRegulatoryNotes.validate();

        if(correctRegulatoryNote){

            String regulatoryNote = textAreaRegulatoryNotes.getText();

            boolean regulatoryNoteExist = false;
            for(String note : regulatoryNotesList){
                if(regulatoryNote.equals(note)){
                    regulatoryNoteExist = true;
                    break;
                }
            }

            if(!regulatoryNoteExist){

                regulatoryNotesList.add(regulatoryNote);
                listViewNewRegulatoryNotes.getItems().add(regulatoryNote);
                textAreaRegulatoryNotes.clear();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Ya se agregó esa nota reglamentaria");
                alert.setContentText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }

        }

    }

}
