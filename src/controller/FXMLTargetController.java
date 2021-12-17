package controller;

import businesslogic.BusinessLogicException;
import businesslogic.StrategyDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import domain.Strategy;
import domain.Target;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLTargetController implements Initializable{

    private final StrategyDAO STRATEGY_DAO = new StrategyDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();
    private ObservableList<Strategy> strategyObservableList;
    private ObservableList<Target> manageWorkPlanTargetsList;
    private Target modifyTarget;
    private static int strategyNumber;
    private int lastIdTargetNumber;
    private Target target;

    @FXML
    private AnchorPane anchorPaneConsultTarget;
    @FXML
    private JFXTextField textFieldTargetTitle;
    @FXML
    private JFXTextArea textAreaTargetDescription;
    @FXML
    private TableView<Strategy> tableViewTargetStrategies;
    @FXML
    private TableColumn<Strategy, Integer> tableColumnStrategyNumber;
    @FXML
    private TableColumn<Strategy, String> tableColumnStrategyDescription;
    @FXML
    private TableColumn<Strategy, String>  tableColumnStrategyGoal;
    @FXML
    private TableColumn<Strategy, String>  tableColumnStrategyAction;
    @FXML
    private TableColumn<Strategy, String>  tableColumnStrategyResult;
    @FXML
    private JFXButton buttonSaveNewTarget;
    @FXML
    private JFXButton buttonUpdateTarget;
    @FXML
    private JFXButton buttonRemoveStrategy;
    @FXML
    private JFXCheckBox checkBoxAchievedTarget;
    @FXML
    private JFXTextArea textAreaStrategyDescription;
    @FXML
    private JFXTextArea textAreaStrategyGoal;
    @FXML
    private JFXTextArea textAreaStrategyAction;
    @FXML
    private JFXTextArea textAreaStrategyResult;
    @FXML
    private Label labelKeyWorkPlan;
    @FXML
    private Label labelTargetActivity;

    public void setKeyWorkPlan(String keyWorkPlan){
        labelKeyWorkPlan.setText(keyWorkPlan);
    }

    public void setLastIdTargetNumber(int lastIdTargetNumber){
        this.lastIdTargetNumber = lastIdTargetNumber;
    }

    public void setModifyTarget(Target modifyTarget){
        this.modifyTarget = modifyTarget;
    }

    public void setStrategyObservableList(ObservableList<Strategy> strategyObservableList) {
        this.strategyObservableList = strategyObservableList;
    }

    public void setManageWorkPlanTargetsList(ObservableList<Target> manageWorkPlanTargetsList){
        this.manageWorkPlanTargetsList = manageWorkPlanTargetsList;
    }

    public void setTargetStrategies(){

        tableViewTargetStrategies.getItems().clear();
        tableViewTargetStrategies.setItems(strategyObservableList);
        strategyNumber = getLastStrategyNumber();
    }

    public void setTargetInformation(Target modifyTarget){
        textFieldTargetTitle.setText(modifyTarget.getTitle());
        textAreaTargetDescription.setText(modifyTarget.getDescription());
        String achievedTarget = "Cumplido";
        if(modifyTarget.getStatus().equals(achievedTarget)){
            checkBoxAchievedTarget.setSelected(true);
        }
    }

    public Target getTarget() {
        return target;
    }

    public ObservableList<Strategy> getStrategyObservableList() {

        return strategyObservableList;
    }

    public void clearStrategyObservableList(){
        strategyObservableList.clear();
    }

    public ObservableList<Target> getManageWorkPlanTargetsList() {
        return manageWorkPlanTargetsList;
    }

    public int getLastStrategyNumber(){
        int getLastStrategNumber=0;
        ObservableList<Strategy> strategyObservableList = tableViewTargetStrategies.getItems();
        for (Strategy strategy : strategyObservableList){
            if(strategy.getNumber() > getLastStrategNumber){
                getLastStrategNumber = strategy.getNumber();
            }
        }
        getLastStrategNumber++;
        return getLastStrategNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        labelTargetActivity.setText("Nuevo objetivo");
        validateText();
        initializeStrategyTable();
        strategyNumber=1;

    }

    public void inicializeModifyTarget(){

        labelTargetActivity.setText("Edición");
        buttonSaveNewTarget.setVisible(false);
        buttonUpdateTarget.setVisible(true);
        checkBoxAchievedTarget.setDisable(false);
        setTargetStrategies();
        setTargetInformation(modifyTarget);
    }

    public void validateText(){

        final String TEXT_PATTERN = "^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1]{1,}[0-9\\sa-zA-ZÀ-ÿ\\u00f1\\u00d1.:',_-]{0,}";

        final StringLengthValidator LENGTH_TITLE_VALIDATOR = new StringLengthValidator(100);
        LENGTH_TITLE_VALIDATOR.setMessage("Sobrepasó los 100 caracteres");

        final StringLengthValidator LENGTH_DESCRIPTION_VALIDATOR = new StringLengthValidator(300);
        LENGTH_DESCRIPTION_VALIDATOR.setMessage("Sobrepasó los 300 caracteres");

        final RequiredFieldValidator REQUIRED_VALIDATOR = new RequiredFieldValidator();
        REQUIRED_VALIDATOR.setMessage("Campo obligatorio");

        final RegexValidator TEXT_VALIDATOR = new RegexValidator();
        TEXT_VALIDATOR.setRegexPattern(TEXT_PATTERN);
        TEXT_VALIDATOR.setMessage("Campo invalido");

        textFieldTargetTitle.getValidators().add(REQUIRED_VALIDATOR);
        textAreaTargetDescription.getValidators().add(REQUIRED_VALIDATOR);
        textAreaStrategyDescription.getValidators().add(REQUIRED_VALIDATOR);
        textAreaStrategyGoal.getValidators().add(REQUIRED_VALIDATOR);
        textAreaStrategyAction.getValidators().add(REQUIRED_VALIDATOR);
        textAreaStrategyResult.getValidators().add(REQUIRED_VALIDATOR);

        textFieldTargetTitle.getValidators().add(TEXT_VALIDATOR);
        textAreaTargetDescription.getValidators().add(TEXT_VALIDATOR);
        textAreaStrategyDescription.getValidators().add(TEXT_VALIDATOR);
        textAreaStrategyGoal.getValidators().add(TEXT_VALIDATOR);
        textAreaStrategyAction.getValidators().add(TEXT_VALIDATOR);
        textAreaStrategyResult.getValidators().add(TEXT_VALIDATOR);

        textFieldTargetTitle.getValidators().add(LENGTH_TITLE_VALIDATOR);

        textAreaTargetDescription.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textAreaStrategyDescription.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textAreaStrategyGoal.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textAreaStrategyAction.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);
        textAreaStrategyResult.getValidators().add(LENGTH_DESCRIPTION_VALIDATOR);

    }

    public void initializeStrategyTable(){

        strategyObservableList = FXCollections.observableArrayList();
        tableColumnStrategyNumber.setCellValueFactory(new PropertyValueFactory<Strategy, Integer>("number"));
        tableColumnStrategyDescription.setCellValueFactory(new PropertyValueFactory<Strategy, String>("description"));
        tableColumnStrategyGoal.setCellValueFactory(new PropertyValueFactory<Strategy, String>("goal"));
        tableColumnStrategyAction.setCellValueFactory(new PropertyValueFactory<Strategy, String>("action"));
        tableColumnStrategyResult.setCellValueFactory(new PropertyValueFactory<Strategy, String>("result"));
    }

    public void clickedAddStrategy(ActionEvent event){

        boolean correctStrategyDescription = textAreaStrategyDescription.validate();
        boolean correctStrategyGoal = textAreaStrategyGoal.validate();
        boolean correctStrategyAction = textAreaStrategyAction.validate();
        boolean correctStrategyResult = textAreaStrategyResult.validate();

        if(correctStrategyDescription && correctStrategyGoal && correctStrategyAction && correctStrategyResult){

            String strategyDescription = textAreaStrategyDescription.getText();
            String strategyGoal = textAreaStrategyGoal.getText();
            String strategyAction = textAreaStrategyAction.getText();
            String strategyResult = textAreaStrategyResult.getText();
            boolean uniqueStrategy = true;

            for(Strategy strategyCheck : strategyObservableList){
                if ((strategyCheck.getDescription().equals(strategyDescription)) &&
                        (strategyCheck.getGoal().equals(strategyGoal)) &&
                        (strategyCheck.getAction().equals(strategyAction)) &&
                        (strategyCheck.getResult().equals(strategyResult))) {
                    uniqueStrategy = false;
                    break;
                }
            }

            if(uniqueStrategy){

                Strategy strategy = new Strategy(strategyAction, strategyDescription, strategyGoal, getLastStrategyNumber(), strategyResult);
                strategyObservableList.add(strategy);
                tableViewTargetStrategies.setItems(strategyObservableList);
                textAreaStrategyDescription.clear();
                textAreaStrategyGoal.clear();
                textAreaStrategyAction.clear();
                textAreaStrategyResult.clear();
                strategyNumber++;

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Registro duplicado");
                alert.setContentText(null);
                alert.setTitle("Registro duplicado");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Favor de corregir los campos inválidos");
            alert.setContentText(null);
            alert.setTitle("Campos inválidos");
            alert.showAndWait();
        }

    }

    public void clickedSaveNewTarget(ActionEvent actionEvent){

        boolean correctTargetTitle = textFieldTargetTitle.validate();
        boolean correctTargetDescription = textAreaTargetDescription.validate();
        boolean correctTargetStrategies = strategyObservableList.isEmpty();

        if(!correctTargetStrategies){
            if(correctTargetTitle && correctTargetDescription) {

                String keyWorkPlan = labelKeyWorkPlan.getText().trim();
                String targetTitle = textFieldTargetTitle.getText();
                String targetDescription = textAreaTargetDescription.getText();
                String status = "Pendiente";
                boolean uniqueTarget = true;

                for(Target workPlanTarget: manageWorkPlanTargetsList){
                    if ((workPlanTarget.getDescription().equals(targetDescription)) &&
                            (workPlanTarget.getTitle().equals(targetTitle)) &&
                            (workPlanTarget.getStatus().equals(status))) {
                        uniqueTarget = false;
                        break;
                    }
                }

                if(uniqueTarget){

                    String idTarget = "TRG-"+ lastIdTargetNumber;
                    target = new Target(idTarget, targetDescription, targetTitle, status, keyWorkPlan);

                    Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    workPlanConfirmation.setTitle("Confirmación");
                    workPlanConfirmation.setHeaderText("Se ha logrado registrar el plan de trabajo correctamente");
                    ButtonType confirmation = new ButtonType("Ok");
                    workPlanConfirmation.getButtonTypes().setAll(confirmation);
                    workPlanConfirmation.showAndWait();
                    addTargetStrategies();

                    Stage stage = (Stage)buttonSaveNewTarget.getScene().getWindow();
                    stage.close();

                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Registro duplicado");
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha completado el registro del objetivo");
            alert.setContentText("No puede haber un objetivo sin ninguna estrategia");
            alert.setTitle("Registro inválido");
            alert.showAndWait();
        }

    }

    public void clickedUpdateTarget(ActionEvent actionEvent){

        boolean correctTargetTitle = textFieldTargetTitle.validate();
        boolean correctTargetDescription = textAreaTargetDescription.validate();
        boolean correctTargetStrategies = strategyObservableList.isEmpty();


        if (!correctTargetStrategies) {

            if (correctTargetTitle && correctTargetDescription) {

                String targetTitle = textFieldTargetTitle.getText();
                String targetDescription = textAreaTargetDescription.getText();
                String keyWorkPlan = labelKeyWorkPlan.getText().trim();
                String status = "Pendiente";
                boolean statusCheckBox = checkBoxAchievedTarget.isSelected();
                if (statusCheckBox) {
                    status = "Cumplido";
                }

                boolean uniqueTarget = true;

                ObservableList<Target> workPlanTargetsList = manageWorkPlanTargetsList;

                for (Target workPlanTarget : workPlanTargetsList) {
                    if ((workPlanTarget.getDescription().equals(targetDescription)) &&
                            (workPlanTarget.getTitle().equals(targetTitle)) &&
                            (workPlanTarget.getStatus().equals(status))) {
                        if(!(modifyTarget.getDescription().equals(targetDescription)) &&
                            !(modifyTarget.getTitle().equals(targetTitle)) &&
                            !(modifyTarget.getStatus().equals(status))){
                            uniqueTarget = false;
                            break;
                        }
                    }
                }

                if (uniqueTarget) {

                    target = new Target(modifyTarget.getIdTarget(), targetDescription, targetTitle, status, keyWorkPlan);

                    Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    workPlanConfirmation.setTitle("Confirmación");
                    workPlanConfirmation.setHeaderText("Actualización completa");
                    workPlanConfirmation.setContentText("El objetivo ha sido actualizado");
                    ButtonType confirmation = new ButtonType("Ok");
                    workPlanConfirmation.getButtonTypes().setAll(confirmation);
                    workPlanConfirmation.showAndWait();

                    addUpdatedTargetStrategies();

                    Stage stage = (Stage) buttonSaveNewTarget.getScene().getWindow();
                    stage.close();


                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Registro duplicado");
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha completado el registro del objetivo");
            alert.setContentText("No puede haber un objetivo sin ninguna estrategia");
            alert.setTitle("Registro inválido");
            alert.showAndWait();
        }


    }

    public void clickedCancelNewTarget(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea cancelar la integracion del objetivo?");
        alert.setContentText(null);
        alert.setTitle("Salir");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                strategyObservableList.clear();
                Stage stage = (Stage) buttonSaveNewTarget.getScene().getWindow();
                stage.setTitle("SGP-CA");
                stage.close();
            }
        }
    }

    public void addTargetStrategies(){

        String idTarget = "TRG-"+ lastIdTargetNumber;

        for(Strategy targetStrategy: strategyObservableList){
            if(targetStrategy.getIdTarget() == null){
                targetStrategy.setIdTarget(idTarget);
            }

        }
    }

    public void addUpdatedTargetStrategies(){
        String idTarget = modifyTarget.getIdTarget();
        for(Strategy targetStrategy : strategyObservableList){
            if(targetStrategy.getIdTarget()== null){
                targetStrategy.setIdTarget(idTarget);
            }
        }
    }

    public void removeTargetStrategy(ActionEvent actionEvent){

        Strategy strategy =  tableViewTargetStrategies.getSelectionModel().getSelectedItem();

        if(strategy!=null){

            Alert workPlanConfirmation = new Alert(Alert.AlertType.INFORMATION);
            workPlanConfirmation.setTitle("Confirmación");
            workPlanConfirmation.setHeaderText("Se ha eliminado la estrategia correctamente");
            ButtonType confirmation = new ButtonType("Ok");
            workPlanConfirmation.getButtonTypes().setAll(confirmation);
            workPlanConfirmation.showAndWait();
            strategyObservableList.remove(strategy);
            tableViewTargetStrategies.refresh();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No se ha seleccionado ninguna estrategia");
            alert.setContentText(null);
            alert.setTitle("Operación inválida");
            alert.showAndWait();
        }
    }

    public void consultTarget(){

        labelTargetActivity.setText("Objetivo");
        anchorPaneConsultTarget.setVisible(true);
        checkBoxAchievedTarget.setDisable(false);
        textFieldTargetTitle.setEditable(false);
        textFieldTargetTitle.setFocusTraversable(false);
        textAreaTargetDescription.setEditable(false);
        textAreaTargetDescription.setFocusTraversable(false);
        tableViewTargetStrategies.setFocusTraversable(false);
        buttonRemoveStrategy.setVisible(false);

        setTargetInformation(modifyTarget);
        consultTargetStrategies(modifyTarget);

        checkBoxAchievedTarget.setDisable(true);
    }

    public void closeConsultTarget(ActionEvent actionEvent){

        textFieldTargetTitle.setEditable(true);
        textFieldTargetTitle.setFocusTraversable(true);
        textAreaTargetDescription.setEditable(true);
        textAreaTargetDescription.setFocusTraversable(true);
        tableViewTargetStrategies.setFocusTraversable(true);
        buttonRemoveStrategy.setVisible(true);
        anchorPaneConsultTarget.setVisible(false);
        Stage stage = (Stage)buttonSaveNewTarget.getScene().getWindow();
        stage.close();

    }

    public void consultTargetStrategies(Target target){

        tableViewTargetStrategies.getItems().clear();
        try {
            List<Strategy> targetStrategiesList = STRATEGY_DAO.foundStrategiesByIdTarget(target.getIdTarget());
            ObservableList<Strategy> strategyObservableList = FXCollections.observableArrayList(targetStrategiesList);
            tableViewTargetStrategies.setItems(strategyObservableList);
            strategyNumber = getLastStrategyNumber();
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

}
