package controller;

import businesslogic.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import domain.Diary;
import domain.Meet;
import domain.Member;
import domain.Prerequirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLScheduleMeetController implements Initializable {
    private MeetDAO meetDAO = new MeetDAO();
    private AlertInterface alertInterface =  new AlertInterface();
    private ObservableList<Prerequirement> observableListPrerequirement = FXCollections.observableArrayList();
    private ObservableList<Diary> observableListDiary= FXCollections.observableArrayList();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @FXML private AnchorPane principalAnchorPane;
    @FXML private JFXTextField textFieldHour;
    @FXML private JFXTextField textFieldPlace;
    @FXML private JFXTextField textFieldProyectName;
    @FXML private JFXTextArea textAreaSubject;
    @FXML private JFXDatePicker datePickerDate;
    @FXML private JFXComboBox<String> comboBoxDescription;
    @FXML private TextArea textAreaDescription;
    @FXML private TableView<Prerequirement> tableDescriptions = new TableView<Prerequirement>();;
    @FXML private TableColumn<Prerequirement, String> tableColumnDescription;
    @FXML private TableColumn<Prerequirement, String> tableColumnMember;
    @FXML private TableView<Diary> tableDiaries = new TableView<Diary>();
    @FXML private TableColumn<Diary, Integer> tableColumnNumber;
    @FXML private TableColumn<Diary, String> tableColumnDiscussionLeader;
    @FXML private TableColumn<Diary, Time> tableColumnEstimatedTime;
    @FXML private TableColumn<Diary, Time> tableColumnRealTime;
    @FXML private TableColumn<Diary, String> tableColumnPointDiscussed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateNewMeet();
        initializeCombobox();
        initializeTablePrerequirements();
        initializeTableDiary();
    }

    public void initializeTablePrerequirements(){ ;
        this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<Prerequirement,String>("description"));
        this.tableColumnMember.setCellValueFactory(new PropertyValueFactory<Prerequirement,String>("idMember"));
        tableDescriptions.setItems(observableListPrerequirement);
    }
    public void initializeTableDiary(){
        this.tableColumnNumber.setCellValueFactory(new PropertyValueFactory<Diary, Integer>("number"));
        this.tableColumnDiscussionLeader.setCellValueFactory(new PropertyValueFactory<Diary,String>("discussionLeader"));
        this.tableColumnEstimatedTime.setCellValueFactory(new PropertyValueFactory<Diary, Time>("estimatedTime"));
        this.tableColumnRealTime.setCellValueFactory(new PropertyValueFactory<Diary, Time>("realTime"));
        this.tableColumnPointDiscussed.setCellValueFactory(new PropertyValueFactory<Diary, String>("pointDiscussed"));
        tableDiaries.setItems(observableListDiary);
    }

    public void initializeCombobox(){
        MemberDAO memberDAO =  new MemberDAO();
        this.comboBoxDescription.setItems(memberDAO.parseToObservableList());
    }

    @FXML
    public void clickedImageAccept(){
        MeetDAO meetDAO = new MeetDAO();

        if(!(observableListPrerequirement == null || observableListDiary == null || datePickerDate.getValue() == null ||
                textAreaSubject.getText().equals("") || textFieldHour.getText().equals("") || textFieldPlace.getText().equals("")
                || textFieldProyectName.getText().equals(""))){
            Meet meet = new Meet(
                    Date.valueOf(datePickerDate.getValue()),
                    textAreaSubject.getText(),
                    Time.valueOf(textFieldHour.getText()),
                    textFieldProyectName.getText(),
                    textFieldPlace.getText(),
                    null
            );

            meetDAO.addMeet(meet);
            textFieldHour.setText("");
            textFieldPlace.setText("");
            textAreaSubject.setText("");
            textFieldProyectName.setText("");

            Optional<ButtonType> action = alertInterface.openConfirmationRegisterData();
            if(action.get() == ButtonType.OK){
                insertPrerequirementsToDatabase();
                insertDiariesToDatabase();
                alertInterface.openAlertSuccesfulInsert();
            }
        }else {
            alertInterface.openAlertWarningIncompleteData();
        }
    }
    private void insertDiariesToDatabase(){
        DiaryDAO diaryDAO = new DiaryDAO();
        for(Diary diary : observableListDiary){
            diaryDAO.addDiary(diary);
        }
    }

    @FXML
    public void clickedImageReturn(){
        Stage stage = (Stage) principalAnchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickedAddPreRequirement(){
        MemberDAO memberDAO = new MemberDAO();
        String idMeetInPrerequirement = "MET-"+ meetDAO.getLastIdMeetNumber();
        if(!(textAreaDescription.getText().equals("") || comboBoxDescription.getValue() == null)){
            try {
                Member member = memberDAO.findMemberByName(comboBoxDescription.getValue());
                Prerequirement prerequirement = new Prerequirement(
                        textAreaDescription.getText(),
                        member.getCurp(),
                        idMeetInPrerequirement
                );
                observableListPrerequirement.add(prerequirement);
                textAreaDescription.setText("");
                comboBoxDescription.setValue("");
            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }
        }else{
            alertInterface.openAlertWarningIncompleteData();
        }

    }

    @FXML
    public void clickedDeletePrerequirement(){

    }



    @FXML
    public void clickedNewDiary(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLNewDiary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException ex){
            Logger.getLogger(FXMLScheduleMeetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void clickedDeleteDiary(){

    }


    private void insertPrerequirementsToDatabase(){
        PrerequirementDAO prerequirementDAO = new PrerequirementDAO();
        for (Prerequirement prerequirement : observableListPrerequirement){
            prerequirementDAO.addPrerequirement(prerequirement);
        }
    }




    public void validateNewMeet(){
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Este campo es obligatorio");

        final String TIME_VALIDATOR = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        RegexValidator validatorHours = new RegexValidator();
        validatorHours.setRegexPattern(TIME_VALIDATOR);
        validatorHours.setMessage("Hora invalida");

        textFieldPlace.setValidators(fieldValidator);
        textFieldProyectName.setValidators(fieldValidator);
        textFieldHour.setValidators(validatorHours);
        textAreaSubject.setValidators(fieldValidator);
        datePickerDate.setValidators(fieldValidator);

        textFieldPlace.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldPlace.validate();
            }
        }));

        textFieldProyectName.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldProyectName.validate();
            }
        }));

        textFieldHour.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textFieldHour.validate();
            }
        }));

        textAreaSubject.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                textAreaSubject.validate();
            }
        }));

        datePickerDate.focusedProperty().addListener(((observableValue, oldValue , newValue )->{
            if(!newValue){
                datePickerDate.validate();
            }
        }));
    }

}
