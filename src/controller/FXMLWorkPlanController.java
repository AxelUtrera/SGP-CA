package controller;

import businesslogic.WorkPlanDAO;
import businesslogic.TargetDAO;
import businesslogic.StrategyDAO;
import businesslogic.MemberDAO;
import businesslogic.AcademicGroupDAO;
import businesslogic.BusinessLogicException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.Strategy;
import domain.Target;
import domain.AcademicGroup;
import domain.Member;
import domain.WorkPlan;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLWorkPlanController implements Initializable{

    private final WorkPlanDAO WORKPLAN_DAO = new WorkPlanDAO();
    private final TargetDAO TARGET_DAO = new TargetDAO();
    private final StrategyDAO STRATEGY_DAO = new StrategyDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AcademicGroupDAO ACADEMICGROUP_DAO = new AcademicGroupDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private ObservableList<Target> pendingTargetsList;
    private ObservableList<Target> achievedTargetsList;
    private ObservableList<Target> manageWorkPlanTargetsList;
    private ObservableList<Strategy> manageTargetStrategiesList;
    private ObservableList<Strategy> manageModifyTargetStrategiesList;
    private ObservableList<Strategy> strategiesRemove;
    private double newTargetPaneX;
    private double newTargetPaneY;
    private String oldKeyWorkPlan="";
    private int targetAdded = 0;
    private static String memberInformation;

    @FXML
    private ComboBox<String> comboBoxWorkPlans;
    @FXML
    private TableView<Target> tableViewWorkPlanPendingTargets;
    @FXML
    private TableView<Target> tableViewWorkPlanAchievedTargets;
    @FXML
    private TableView<Target> tableViewManageWorkPlanTargets;
    @FXML
    private TableColumn<Target, String> tableColumnTargetPendingTitle;
    @FXML
    private TableColumn<Target, String> tableColumnTargetAchievedTitle;
    @FXML
    private TableColumn<Target, String> tableColumnManageWorkPlanTargetsTitle;
    @FXML
    private Label labelAcademicGroupName;
    @FXML
    private Label labelMemberName;
    @FXML
    private Label labelMemberInstitutionalMail;
    @FXML
    private Label labelMemberAcademicRole;
    @FXML
    private Label labelMemberInformation;
    @FXML
    private JFXTextField textFieldKeyWorkPlan;
    @FXML
    private JFXTextField textFieldManageKeyWorkPlan;
    @FXML
    private DatePicker datePickerStartDate;
    @FXML
    private DatePicker datePickerEndDate;
    @FXML
    private DatePicker datePickerManageStartDate;
    @FXML
    private DatePicker datePickerManageEndDate;
    @FXML
    private ImageView imageShowLateralMenu;
    @FXML
    private ImageView imageHideLateralMenu;
    @FXML
    private AnchorPane anchorPaneLateralMenu;
    @FXML
    private AnchorPane anchorPaneAddWorkPlan;
    @FXML
    private AnchorPane anchorPaneHideLateralMenu;
    @FXML
    private AnchorPane anchorPaneTargetsAchieved;
    @FXML
    private AnchorPane anchorPaneManageWorkPlan;
    @FXML
    private AnchorPane anchorPaneManageWorkPlanInformation;
    @FXML
    private Button buttonShowTargetsAchieved;
    @FXML
    private Button buttonHideTargetsAchieved;
    @FXML
    private Button buttonManageWorkPlan;
    @FXML
    private JFXButton buttonEvents;
    @FXML
    private JFXButton buttonProduction;
    @FXML
    private JFXButton buttonMembers;
    @FXML
    private JFXButton buttonExitWorkPlan;
    @FXML
    private Button buttonAddWorkPlan;
    @FXML
    private ImageView imageViewRefreshPendingTargets;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        setComboBoxWithWorkPlans();
        anchorPaneLateralMenu.setVisible(true);
        anchorPaneTargetsAchieved.setVisible(true);
        buttonHideTargetsAchieved.setVisible(true);
        lateralMenu();
        targetsAchievedPane();
        validateNewKeyWorkPlan();
        initializeWorkPlanTargetsTable();
        clickedTarget();
        if(memberInformation!=null){
            labelMemberInformation.setText(memberInformation);
            try {
                Member member = MEMBER_DAO.findMemberByCurp(getCurpMember());
                if(member.getAcademicRole().equals("Responsable")){
                    buttonAddWorkPlan.setDisable(false);
                }else{
                    buttonAddWorkPlan.setDisable(true);
                }
            }catch(BusinessLogicException ex){
                ALERT_INTERFACE.openAlertFailedInsert();
            }

        }
    }

    public void setMemberInformation(Member memberLogin){

        String member = memberLogin.getName()+"["+memberLogin.getCurp()+"]";
        memberInformation = member;
        labelMemberInformation.setText(member);

        if(memberLogin.getAcademicRole().equals("Responsable")){
            buttonAddWorkPlan.setDisable(false);
        }else{
            buttonAddWorkPlan.setDisable(true);
        }
    }

    public void validateNewKeyWorkPlan(){

        final String TEXT_PATTERN = "^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[0-9\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        final StringLengthValidator LENGTH_VALIDATOR = new StringLengthValidator(45);
        LENGTH_VALIDATOR.setMessage("Sobrepasó los 45 caracteres");

        final RequiredFieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();
        REQUIRED_VALIDATOR.setMessage("Campo obligatorio");

        final RegexValidator TEXT_VALIDATOR = new RegexValidator();
        TEXT_VALIDATOR.setRegexPattern(TEXT_PATTERN);
        TEXT_VALIDATOR.setMessage("Campo inválido");

        textFieldKeyWorkPlan.getValidators().add(REQUIRED_VALIDATOR);
        textFieldKeyWorkPlan.getValidators().add(TEXT_VALIDATOR);
        textFieldKeyWorkPlan.getValidators().add(LENGTH_VALIDATOR);
    }

    public void deleteEmptyWorkPlans(){

        try {
            List<WorkPlan> workPlanList = WORKPLAN_DAO.displayAllWorkPlans();

            for (WorkPlan workPlan : workPlanList) {
                List<Target> workPlanTargets = TARGET_DAO.foundTargetsByKeyWorkplan(workPlan.getKeyWorkPlan());
                if (workPlanTargets.isEmpty()) {
                    WORKPLAN_DAO.deleteWorkPlan(workPlan.getKeyWorkPlan());
                }
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void initializeWorkPlanTargetsTable(){

        pendingTargetsList = FXCollections.observableArrayList();
        tableColumnTargetPendingTitle.setCellValueFactory(new PropertyValueFactory<Target, String>("title"));

        achievedTargetsList = FXCollections.observableArrayList();
        tableColumnTargetAchievedTitle.setCellValueFactory(new PropertyValueFactory<Target, String>("title"));

        manageWorkPlanTargetsList = FXCollections.observableArrayList();
        tableColumnManageWorkPlanTargetsTitle.setCellValueFactory(new PropertyValueFactory<Target, String>("title"));


        manageTargetStrategiesList = FXCollections.observableArrayList();
        manageModifyTargetStrategiesList = FXCollections.observableArrayList();
        strategiesRemove = FXCollections.observableArrayList();

    }

    public void setComboBoxWithWorkPlans(){

        try {
            deleteEmptyWorkPlans();

            ObservableList<String> workPlanObservableList = FXCollections.observableArrayList();
            List<WorkPlan> workPlanList = WORKPLAN_DAO.displayAllWorkPlans();

            for (WorkPlan workPlan : workPlanList) {
                String keyWorkPlan = workPlan.getKeyWorkPlan();
                String workPlanStartYear = String.valueOf(workPlan.getStartDate().getYear() + 1900);
                String workPlanEndYear = String.valueOf(workPlan.getEndDate().getYear() + 1900);
                String comboBoxWorkPlans = keyWorkPlan + " [" + workPlanStartYear + "-" + workPlanEndYear + "]";
                workPlanObservableList.add(comboBoxWorkPlans);
            }

            comboBoxWorkPlans.setItems(workPlanObservableList);

        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public String getKeyWorkPlanOfComboxSelected(){

        String comboBoxWorkPlanSelected = comboBoxWorkPlans.getSelectionModel().getSelectedItem();
        StringBuilder keyWorkPlan = new StringBuilder();
        boolean keyWorkPlanComplete = false;
        int keyWorkPlanCharacter = 0;

        do{
            if(comboBoxWorkPlanSelected.charAt(keyWorkPlanCharacter) == '['){
                keyWorkPlanComplete=true;
            }else{
                keyWorkPlan.append(comboBoxWorkPlanSelected.charAt(keyWorkPlanCharacter));
                keyWorkPlanCharacter++;
            }

        }while(!keyWorkPlanComplete);

        return keyWorkPlan.toString().trim();
    }

    public void selectComboBoxWorkPlan(ActionEvent actionEvent){

        tableViewWorkPlanPendingTargets.getItems().clear();
        tableViewWorkPlanAchievedTargets.getItems().clear();
        try {
            if ((comboBoxWorkPlans.getSelectionModel().getSelectedItem()) != null) {

                String keyWorkPlan = getKeyWorkPlanOfComboxSelected();
                WorkPlan workPlan = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);
                if (!(workPlan == null)) {

                    Member member = MEMBER_DAO.findMemberByCurp(getCurpMember());
                    if(member.getAcademicRole().equals("Responsable")){
                        buttonManageWorkPlan.setDisable(false);
                    }
                    String curpMember = workPlan.getCurpMember();
                    setResponsableInformationOfWorkPlan(curpMember);
                    setWorkPlanTargets(keyWorkPlan);
                }
            } else {
                buttonManageWorkPlan.setDisable(true);
                labelAcademicGroupName.setText("");
                labelMemberName.setText("");
                labelMemberInstitutionalMail.setText("");
                labelMemberAcademicRole.setText("");

            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void setResponsableInformationOfWorkPlan(String curp){

        try {
            Member member = MEMBER_DAO.findMemberByCurp(curp);
            if (!(member == null)) {
                AcademicGroup memberAcademicGroup = ACADEMICGROUP_DAO.foundAcademicGroupByID(member.getKeyAcademicGroup());
                String memberName = member.getName();
                String memberInstitutionalMail = member.getInstitutionalMail();
                String memberAcademicRole = member.getAcademicRole();
                labelAcademicGroupName.setText(memberAcademicGroup.getName());
                labelMemberName.setText(memberName);
                labelMemberInstitutionalMail.setText(memberInstitutionalMail);
                labelMemberAcademicRole.setText(memberAcademicRole);
            }
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void setWorkPlanTargets(String keyWorkPlan){

        try {
            List<Target> workPlanPendingTargetsList = TARGET_DAO.foundPendingTargetsByKeyWorkplan(keyWorkPlan);
            pendingTargetsList.addAll(workPlanPendingTargetsList);

            List<Target> workPlanAchievedTargetsList = TARGET_DAO.foundAchievedTargetsByKeyWorkplan(keyWorkPlan);
            achievedTargetsList.addAll(workPlanAchievedTargetsList);

            tableViewWorkPlanPendingTargets.setItems(pendingTargetsList);
            tableViewWorkPlanAchievedTargets.setItems(achievedTargetsList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void clickedTarget(){

        tableViewWorkPlanPendingTargets.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                consultTarget(tableViewWorkPlanPendingTargets.getSelectionModel().getSelectedItem());
            }
        });

        tableViewWorkPlanAchievedTargets.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                consultTarget(tableViewWorkPlanAchievedTargets.getSelectionModel().getSelectedItem());
            }
        });

    }

    public void lateralMenu(){

        hideLateralMenu();

        imageShowLateralMenu.setOnMouseClicked(event -> {
            imageShowLateralMenu.setVisible(false);
            showLateralMenu();
        });

        imageHideLateralMenu.setOnMouseClicked(event -> {
            hideLateralMenu();
            showAnchorPaneHideLateralMenu();
            imageShowLateralMenu.setVisible(true);
        });

    }

    public void showLateralMenu(){

        TranslateTransition translateTransitionHideLateralMenu = new TranslateTransition(Duration.seconds(0.4), anchorPaneHideLateralMenu);
        translateTransitionHideLateralMenu.setByX(-55);
        translateTransitionHideLateralMenu.play();

        TranslateTransition translateTransitionLateralMenu = new TranslateTransition(Duration.seconds(0.4), anchorPaneLateralMenu);
        translateTransitionLateralMenu.setByX(+497);
        translateTransitionLateralMenu.play();
    }

    public void hideLateralMenu(){

        TranslateTransition translateTransitionLateralMenu = new TranslateTransition(Duration.seconds(0.4), anchorPaneLateralMenu);
        translateTransitionLateralMenu.setByX(-497);
        translateTransitionLateralMenu.play();

    }

    public void showAnchorPaneHideLateralMenu(){

        TranslateTransition translateTransitionHideLateralMenu = new TranslateTransition(Duration.seconds(0.4), anchorPaneHideLateralMenu);
        translateTransitionHideLateralMenu.setByX(+55);
        translateTransitionHideLateralMenu.play();
    }

    public void targetsAchievedPane(){

        hideTargetsAchievedPane();

        buttonShowTargetsAchieved.setOnMouseClicked(event -> {
            buttonShowTargetsAchieved.setVisible(false);
            showTargetsAchievedPane();
        });

        buttonHideTargetsAchieved.setOnMouseClicked(event -> {
            hideTargetsAchievedPane();
            buttonShowTargetsAchieved.setVisible(true);
        });

    }

    public void showTargetsAchievedPane(){

        TranslateTransition translateTransitionButtonHide= new TranslateTransition(Duration.seconds(0.4), buttonHideTargetsAchieved);
        translateTransitionButtonHide.setByX(-322);
        translateTransitionButtonHide.play();

        TranslateTransition translateTransitionTargetsAchievedPane = new TranslateTransition(Duration.seconds(0.4), anchorPaneTargetsAchieved);
        translateTransitionTargetsAchievedPane.setByX(-297);
        translateTransitionTargetsAchievedPane.play();
    }

    public void hideTargetsAchievedPane(){

        TranslateTransition translateTransitionButtonHide = new TranslateTransition(Duration.seconds(0.4), buttonHideTargetsAchieved);
        translateTransitionButtonHide.setByX(+322);
        translateTransitionButtonHide.play();

        TranslateTransition translateTransitionTargetsAchievedPane = new TranslateTransition(Duration.seconds(0.4), anchorPaneTargetsAchieved);
        translateTransitionTargetsAchievedPane.setByX(+297);
        translateTransitionTargetsAchievedPane.play();

    }

    public void clickedAddWorkPlan(ActionEvent actionEvent){

        imageViewRefreshPendingTargets.setVisible(false);
        if(!(imageShowLateralMenu.isVisible())){

            hideLateralMenu();
            showAnchorPaneHideLateralMenu();
            imageShowLateralMenu.setVisible(true);

        }

        if(!(buttonShowTargetsAchieved.isVisible())){
            hideTargetsAchievedPane();
            buttonShowTargetsAchieved.setVisible(true);
        }

        anchorPaneAddWorkPlan.setVisible(true);
        datePickerStartDate.setValue(LocalDate.now());
        datePickerStartDate.setEditable(false);
        datePickerEndDate.setValue(LocalDate.now().plusDays(1));
        datePickerEndDate.setEditable(false);
    }

    public void clickedExitAddWorkplan(ActionEvent actionEvent){
        anchorPaneAddWorkPlan.setVisible(false);
        imageViewRefreshPendingTargets.setVisible(true);
    }

    public boolean validateStartDate(Date startDate, Date endDate){

        boolean correctStarDate=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date actualDate = new Date();
        try {
            Date actualDateSimpleFormat = dateFormat.parse(dateFormat.format(actualDate));

            if ((actualDateSimpleFormat.before(startDate) || actualDateSimpleFormat.equals(startDate)) && startDate.before(endDate)) {
                correctStarDate = true;
            }
        }catch (ParseException parseException){
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, parseException);
        }
        return correctStarDate;
    }

    public boolean validateEndDate(Date endDate){

        boolean correctEndDate =false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date actualDate = new Date();
        try{
            Date actualDateSimpleFormat = dateFormat.parse(dateFormat.format(actualDate));

            if(actualDateSimpleFormat.before(endDate)){
                correctEndDate=true;
            }
        }catch (ParseException parseException){
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, parseException);
        }

        return correctEndDate;
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

    public void clickedContinueAddWorkPlan(ActionEvent actionEvent){

        Date startDate = Date.from(datePickerStartDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(datePickerEndDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        boolean correctKeyWorkPlan = textFieldKeyWorkPlan.validate();

        boolean correctEndDate = validateEndDate(endDate);
        boolean correctStartDate = validateStartDate(startDate, endDate);

        try {
            if (correctKeyWorkPlan && correctEndDate && correctStartDate) {

                String keyWorkPlan = textFieldKeyWorkPlan.getText();
                String curpMember = getCurpMember();
                Member member = MEMBER_DAO.findMemberByCurp(curpMember);
                String keyAcademicGroup = member.getKeyAcademicGroup();

                WorkPlan uniqueKeyWorkPlan = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);

                if (uniqueKeyWorkPlan == null) {
                    WorkPlan workPlan = new WorkPlan(keyWorkPlan, startDate, endDate, keyAcademicGroup, curpMember);
                    boolean operationResult = WORKPLAN_DAO.addWorkPlan(workPlan);

                    if (operationResult) {

                        Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                        workPlanConfirmation.setTitle("Confirmación");
                        workPlanConfirmation.setHeaderText("Se ha logrado registrar el plan de trabajo correctamente");
                        ButtonType confirmation = new ButtonType("Ok");
                        workPlanConfirmation.getButtonTypes().setAll(confirmation);
                        workPlanConfirmation.showAndWait();
                        oldKeyWorkPlan = keyWorkPlan;
                        manageNewWorkPlan(keyWorkPlan);
                        textFieldKeyWorkPlan.clear();
                        anchorPaneAddWorkPlan.setVisible(false);


                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Base de datos no disponible por el momento");
                        alert.setContentText(null);
                        alert.setTitle("Error en conexión");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Ya existe un plan de trabajo con esa clave");
                    alert.setContentText(null);
                    alert.setTitle("Registro duplicado");
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

    }

    public void clickedManageWorkPlan(ActionEvent actionEvent){

        try {
            String keyWorkPlan = getKeyWorkPlanOfComboxSelected();
            oldKeyWorkPlan = keyWorkPlan;
            tableViewManageWorkPlanTargets.getItems().clear();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            anchorPaneManageWorkPlanInformation.setDisable(false);
            anchorPaneManageWorkPlan.setVisible(true);

            WorkPlan workPlan = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);
            LocalDate startLocalDate = LocalDate.parse(simpleDateFormat.format(workPlan.getStartDate()));
            LocalDate endLocalDate = LocalDate.parse(simpleDateFormat.format(workPlan.getEndDate()));
            textFieldManageKeyWorkPlan.setText(keyWorkPlan);
            datePickerManageStartDate.setValue(startLocalDate);
            datePickerManageEndDate.setValue(endLocalDate);
            setManageWorkPlanTargets(keyWorkPlan);
            List<Strategy> strategyList = STRATEGY_DAO.displayAllStrategies();
            manageTargetStrategiesList.addAll(strategyList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void manageNewWorkPlan(String keyWorkPlan){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Agregue por lo menos un objetivo para completar el registro");
        alert.setContentText(null);
        alert.setTitle("Completar registro");
        ButtonType confirmation = new ButtonType("Ok");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            anchorPaneManageWorkPlan.setVisible(true);
            WorkPlan workPlan = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);
            LocalDate startLocalDate = LocalDate.parse(simpleDateFormat.format(workPlan.getStartDate()));
            LocalDate endLocalDate = LocalDate.parse(simpleDateFormat.format(workPlan.getEndDate()));
            textFieldManageKeyWorkPlan.setText(keyWorkPlan);
            datePickerManageStartDate.setValue(startLocalDate);
            datePickerManageEndDate.setValue(endLocalDate);
            setManageWorkPlanTargets(keyWorkPlan);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    public void clickedAddTarget(ActionEvent actionEvent){

        try{

            String keyWorkPlan = textFieldManageKeyWorkPlan.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLTarget.fxml"));
            Parent root = fxmlLoader.load();
            FXMLTargetController fxmlTargetController = fxmlLoader.getController();
            fxmlTargetController.setKeyWorkPlan(keyWorkPlan);
            fxmlTargetController.setLastIdTargetNumber(TARGET_DAO.getLastIdTargetNumber() + targetAdded);
            fxmlTargetController.setManageWorkPlanTargetsList(manageWorkPlanTargetsList);

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

            Target target = fxmlTargetController.getTarget();
            if (target != null) {
                manageWorkPlanTargetsList.add(target);
                targetAdded++;
                tableViewManageWorkPlanTargets.refresh();
            }

            manageTargetStrategiesList.addAll(fxmlTargetController.getStrategyObservableList());

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);

        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void consultTarget(Target target){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLTarget.fxml"));
            Parent root = fxmlLoader.load();
            FXMLTargetController fxmlTargetController = fxmlLoader.getController();

            fxmlTargetController.setModifyTarget(target);
            fxmlTargetController.consultTarget();

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


        }catch (IOException ex) {
                Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedModifyTarget(ActionEvent actionEvent){

        Target targetSelected = tableViewManageWorkPlanTargets.getSelectionModel().getSelectedItem();

        if(targetSelected != null){
            try{

                String keyWorkPlan = textFieldManageKeyWorkPlan.getText();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLTarget.fxml"));
                Parent root = fxmlLoader.load();
                FXMLTargetController fxmlTargetController = fxmlLoader.getController();

                for(Strategy strategy : manageTargetStrategiesList){
                    if(targetSelected.getIdTarget().equals(strategy.getIdTarget())){
                        manageModifyTargetStrategiesList.add(strategy);
                    }
                }

                fxmlTargetController.setKeyWorkPlan(keyWorkPlan);
                fxmlTargetController.setModifyTarget(targetSelected);
                fxmlTargetController.setStrategyObservableList(manageModifyTargetStrategiesList);
                fxmlTargetController.setManageWorkPlanTargetsList(manageWorkPlanTargetsList);
                fxmlTargetController.inicializeModifyTarget();

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

                Target target = fxmlTargetController.getTarget();

                if (target != null) {
                    Target targetRemove = null;
                    for(Target manageTarget : manageWorkPlanTargetsList){
                        if(manageTarget.getIdTarget().equals(target.getIdTarget())){
                            targetRemove = manageTarget;
                            break;
                        }
                    }
                    manageWorkPlanTargetsList.remove(targetRemove);
                    manageWorkPlanTargetsList.add(target);
                }

                ObservableList<Strategy> strategyObservableList = fxmlTargetController.getStrategyObservableList();

                if(target!=null) {
                    for(Strategy strategy : manageTargetStrategiesList){
                        if(strategy.getIdTarget().equals(target.getIdTarget())){
                            strategiesRemove.add(strategy);
                        }
                    }
                    manageTargetStrategiesList.removeAll(strategiesRemove);
                    manageTargetStrategiesList.addAll(strategyObservableList);
                }

                fxmlTargetController.clearStrategyObservableList();

            } catch (IOException ex) {
                Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ningun objetivo");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }

    }

    public boolean validateChangesManageWorkPlan(){

        boolean changesInWorkPlanInformation = true;
        String keyWorkPlan = textFieldManageKeyWorkPlan.getText();

        if(keyWorkPlan.equals(oldKeyWorkPlan)){
            changesInWorkPlanInformation = false;
        }
        return changesInWorkPlanInformation;
    }

    public void setManageWorkPlanTargets(String keyWorkPlan){

        try {
            List<Target> workPlanPendingTargetsList = TARGET_DAO.foundPendingTargetsByKeyWorkplan(keyWorkPlan);
            manageWorkPlanTargetsList.addAll(workPlanPendingTargetsList);

            tableViewManageWorkPlanTargets.setItems(manageWorkPlanTargetsList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void clickedSaveManageWorkPlan(ActionEvent actionEvent){

        Date startDate = Date.from(datePickerManageStartDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(datePickerManageEndDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        boolean correctKeyWorkPlan = textFieldManageKeyWorkPlan.validate();

        boolean correctEndDate = validateEndDate(endDate);
        boolean correctStartDate = validateStartDate(startDate, endDate);

        if(!manageWorkPlanTargetsList.isEmpty()){

            try {
                WorkPlan workPlanValidateDates = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(oldKeyWorkPlan);

                if (workPlanValidateDates != null) {
                    if (startDate.equals(workPlanValidateDates.getStartDate())) {
                        correctStartDate = true;
                    }
                    if (endDate.equals(workPlanValidateDates.getEndDate())) {
                        correctEndDate = true;
                    }
                }

                if (correctKeyWorkPlan) {

                    if (correctEndDate && correctStartDate) {

                        String keyWorkPlan = textFieldManageKeyWorkPlan.getText();
                        String curpMember = getCurpMember();
                        Member member = MEMBER_DAO.findMemberByCurp(curpMember);
                        String keyAcademicGroup = member.getKeyAcademicGroup();

                        WorkPlan uniqueKeyWorkPlan = WORKPLAN_DAO.foundWorkPlanByKeyWorkPlan(keyWorkPlan);

                        if (uniqueKeyWorkPlan == null || !validateChangesManageWorkPlan()) {

                            WorkPlan workPlan = new WorkPlan(keyWorkPlan, startDate, endDate, keyAcademicGroup, curpMember);
                            boolean operationUpdateWorkPlan = WORKPLAN_DAO.updateWorkPlan(workPlan, oldKeyWorkPlan);

                            if (operationUpdateWorkPlan) {

                                for (Target workPlanTarget : manageWorkPlanTargetsList) {

                                    Target targetFound = TARGET_DAO.foundTargetByIdTarget(workPlanTarget.getIdTarget());
                                    if (targetFound == null) {
                                        workPlanTarget.setKeyWorkplan(keyWorkPlan);
                                        TARGET_DAO.addTarget(workPlanTarget);
                                    } else {
                                        workPlanTarget.setKeyWorkplan(keyWorkPlan);
                                        TARGET_DAO.updateTarget(workPlanTarget);
                                    }
                                }

                                for (Strategy strategyRemove : strategiesRemove) {
                                    boolean strategyExist = false;
                                    for (Strategy strategy : manageTargetStrategiesList) {
                                        if (strategyRemove.getDescription().equals(strategy.getDescription()) &&
                                                strategyRemove.getAction().equals(strategy.getAction()) &&
                                                strategyRemove.getGoal().equals(strategy.getGoal()) &&
                                                strategyRemove.getNumber() == strategy.getNumber() &&
                                                strategyRemove.getResult().equals(strategy.getResult()) &&
                                                strategyRemove.getIdTarget().equals(strategy.getIdTarget())) {
                                            strategyExist = true;
                                            break;
                                        }
                                    }
                                    if (!strategyExist) {
                                        STRATEGY_DAO.deleteStrategy(strategyRemove.getIdStrategy());
                                    }

                                }

                                for (Strategy strategy : manageTargetStrategiesList) {
                                    Strategy strategyFound = STRATEGY_DAO.foundStrategyByIdStrategy(strategy.getIdStrategy());
                                    if (strategyFound == null) {
                                        STRATEGY_DAO.addStrategy(strategy);
                                    }
                                }

                                Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                                workPlanConfirmation.setTitle("Confirmación");
                                workPlanConfirmation.setHeaderText("Cambios registrados exitosamente sobre el plan de trabajo");
                                ButtonType confirmation = new ButtonType("Ok");
                                workPlanConfirmation.getButtonTypes().setAll(confirmation);
                                workPlanConfirmation.showAndWait();

                                targetAdded = 0;
                                manageWorkPlanTargetsList.clear();
                                manageTargetStrategiesList.clear();
                                strategiesRemove.clear();
                                comboBoxWorkPlans.getSelectionModel().clearSelection();
                                setComboBoxWithWorkPlans();
                                tableViewWorkPlanPendingTargets.refresh();
                                tableViewWorkPlanAchievedTargets.refresh();
                                tableViewManageWorkPlanTargets.getItems().clear();

                                anchorPaneManageWorkPlan.setVisible(false);

                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Base de datos no disponible por el momento");
                                alert.setContentText(null);
                                alert.setTitle("Error en conexión");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Ya existe un plan de trabajo con esa clave");
                            alert.setContentText(null);
                            alert.setTitle("Registro duplicado");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Favor de corregir las fechas inválidas");
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
            alert.setHeaderText("No puede haber un plan de trabajo sin ningún objetivo");
            alert.setContentText(null);
            alert.setTitle("Registro incompleto");
            alert.showAndWait();
        }

    }

    public void clickedExitManageWorkPlan(ActionEvent actionEvent){

        boolean incompleteRegister = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea salir?");
        alert.setContentText("Ningún cambio será almacenado en el sistema");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent()){
            if (action.get() == ButtonType.OK) {

                try {

                    List<Target> workPlanTargets = TARGET_DAO.foundTargetsByKeyWorkplan(oldKeyWorkPlan);
                    if (workPlanTargets.isEmpty()) {
                        incompleteRegister = WORKPLAN_DAO.deleteWorkPlan(oldKeyWorkPlan);
                    }

                    targetAdded = 0;
                    manageWorkPlanTargetsList.clear();
                    manageTargetStrategiesList.clear();
                    strategiesRemove.clear();
                    comboBoxWorkPlans.getSelectionModel().clearSelection();
                    setComboBoxWithWorkPlans();
                    anchorPaneManageWorkPlan.setVisible(false);
                }catch(BusinessLogicException ex){
                    ALERT_INTERFACE.openAlertFailedInsert();
                }
            }
        }

        if(incompleteRegister){
            Alert alertIncompleteRegister = new Alert(Alert.AlertType.INFORMATION);
            alertIncompleteRegister.setHeaderText("No se ha completado el registro del plan de trabajo");
            alertIncompleteRegister.setContentText("No puede haber un plan de trabajo sin ningún objetivo");
            alertIncompleteRegister.setTitle("Registro anulado");
            ButtonType confirmation = new ButtonType("Ok");
            alertIncompleteRegister.getButtonTypes().setAll(confirmation);
            alertIncompleteRegister.showAndWait();
        }

    }

    public void clickedMembers(ActionEvent actionEvent){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLMemberList.fxml"));
            Parent root = fxmlLoader.load();
            FXMLMemberListController controller = fxmlLoader.getController();
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
            stage.show();

            stage.setOnCloseRequest(event -> controller.returnToWorkPlanPane());

            Stage stageClose = (Stage) buttonMembers.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedEvents(ActionEvent actionEvent){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLHistoryEvent.fxml"));
            Parent root = fxmlLoader.load();
            FXMLHistoryEventController controller = fxmlLoader.getController();
            controller.setMemberInformation(labelMemberInformation.getText());

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
            stage.show();

            stage.setOnCloseRequest(event -> controller.clickedImageReturn());

            Stage stageClose = (Stage) buttonEvents.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedProduction(ActionEvent actionEvent){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLMenuEvidences.fxml"));
            Parent root = fxmlLoader.load();
            FXMLMenuEvidencesController controller = fxmlLoader.getController();
            controller.setMemberInformation(labelMemberInformation.getText());

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
            stage.show();

            stage.setOnCloseRequest(event -> controller.returnToWorkPlanPane());

            Stage stageClose = (Stage) buttonProduction.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clickedProjects(ActionEvent actionEvent){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../user/FXMLInvestigationProjectList.fxml"));
            Parent root = fxmlLoader.load();
            FXMLInvestigationProjectListController controller = fxmlLoader.getController();
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
            stage.show();

            stage.setOnCloseRequest(event -> controller.clickedExit());

            Stage stageClose = (Stage) buttonEvents.getScene().getWindow();
            stageClose.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshPendingTargetsTable(MouseEvent mouseEvent){
        tableViewWorkPlanPendingTargets.getSelectionModel().clearSelection();
    }

    public void refreshAchievedTargetsTable(MouseEvent mouseEvent){
        tableViewWorkPlanAchievedTargets.getSelectionModel().clearSelection();
    }

    public void clickedExitWorkPlan(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir del sistema?");
        alert.setContentText("Su sesión será cerrada");
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/FXMLLogin.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setTitle("SGP-CA");
                    stage.setScene(scene);
                    stage.show();

                    Stage stageReturn = (Stage) buttonExitWorkPlan.getScene().getWindow();
                    stageReturn.close();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLWorkPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }


    }

}
