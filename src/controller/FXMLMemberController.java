package controller;

import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FXMLMemberController implements Initializable {


    @FXML private AnchorPane scenePane;

    @FXML private JFXTextField textFieldCURP;

    @FXML private JFXTextField textFieldNom;

    @FXML private JFXDatePicker datePicker;

    @FXML private JFXTextField textFieldCorreo;

    @FXML private JFXTextField textFieldNumero;

    @FXML private JFXTextField textFieldContrasenia;

    @FXML private ComboBox<String> comboBox;

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Integrante", "Colaborador");
        comboBox.setItems(list);
        validateCURP();
        validateName();
        validateEmail();
        validateTelephone();
        validatePassword();
    }

    public boolean validateDate(){
        boolean validDate = false;
        java.util.Date deadline = new Date((2002-1900), (1-1), 1);
        java.util.Date dateObtained = Date.valueOf(datePicker.getValue());
        if (datePicker.validate() && dateObtained.before(deadline)) {
            validDate = true;
        } else {
            datePicker.setValue(null);
            datePicker.resetValidation();
        }
        return validDate;
    }

    @FXML public void register() throws SQLException {
        if(!(textFieldCURP.getText().equals("") || textFieldNom.getText().equals("") || textFieldCorreo.getText().equals("")
                || textFieldNumero.getText().equals("")|| textFieldContrasenia.getText().equals("") ||datePicker.getValue() == null ||comboBox.getValue() == null)){
            if(validateCURP()==true && validateName()==true && validateEmail()==true && validateTelephone()==true && validatePassword()==true && validateDate() == true) {
                Member member = new Member(
                        textFieldCURP.getText(),
                        textFieldNom.getText(),
                        Date.valueOf(datePicker.getValue()),
                        textFieldCorreo.getText(),
                        textFieldNumero.getText(),
                        comboBox.getSelectionModel().getSelectedItem(),
                        "AGP-1",
                        textFieldContrasenia.getText());
                try {
                    if ((MEMBER_DAO.findMemberByCurp(textFieldCURP.getText())) == null && (MEMBER_DAO.findMemberByInstitutionalMail(textFieldCorreo.getText()) == null)){ MEMBER_DAO.addMember(member);
                        textFieldCURP.setText("");
                        textFieldNom.setText("");
                        textFieldCorreo.setText("");
                        textFieldNumero.setText("");
                        textFieldContrasenia.setText("");
                        datePicker.setValue(null);
                        comboBox.setValue(null);
                        ALERT_INTERFACE.openAlertSuccesfulInsert();
                    }else {
                        ALERT_INTERFACE.openAlertDuplicatedData();
                    }
                } catch (BusinessLogicException ex) {
                    ALERT_INTERFACE.openAlertFailedInsert();
                }


            }else{
                ALERT_INTERFACE.openAlertInvalidData();
            }
        }else{
            ALERT_INTERFACE.openAlertWarningIncompleteData();
        }
    }

    @FXML private void clickCancel(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar el registro?");
        alert.setContentText("No se realizará ningún cambio en el sistema");
        alert.setTitle("Cancelar");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }

    }

    private boolean validateCURP(){
        final String TEXT_PATTERN =
                "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
                        "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                        "[HM]{1}" +
                        "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                        "[B-DF-HJ-NP-TV-Z]{3}" +
                        "[0-9A-Z]{1}[0-9]{1}$";

        Pattern patron = Pattern.compile(TEXT_PATTERN);
        if(!patron.matcher(textFieldCURP.getText()).matches()) {
            RegexValidator textValidator = new RegexValidator();
            textValidator.setRegexPattern(TEXT_PATTERN);
            textValidator.setMessage("Campo invalido");
            textFieldCURP.getValidators().add(textValidator);

            textFieldCURP.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    textFieldCURP.validate();
                }
            }));
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName() {
        final String TEXT_PATTERN = "^([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\']+[\\s])+([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])+" + "[\\s]?([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])?$";

        Pattern patron = Pattern.compile(TEXT_PATTERN);
        if (!patron.matcher(textFieldNom.getText()).matches()) {
            RegexValidator textValidator = new RegexValidator();
            textValidator.setRegexPattern(TEXT_PATTERN);
            textValidator.setMessage("Campo invalido");
            textFieldNom.getValidators().add(textValidator);

            textFieldNom.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (!newValue) {
                    textFieldNom.validate();
                }
            }));
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        final String TEXT_PATTERN = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";

        Pattern patron = Pattern.compile(TEXT_PATTERN);
        if (!patron.matcher(textFieldCorreo.getText()).matches()) {
            RegexValidator textValidator = new RegexValidator();
            textValidator.setRegexPattern(TEXT_PATTERN);
            textValidator.setMessage("Campo invalido");
            textFieldCorreo.getValidators().add(textValidator);

            textFieldCorreo.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (!newValue) {
                    textFieldCorreo.validate();
                }
            }));
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTelephone() {
        final String TEXT_PATTERN = "^(\\d{10})$";

        Pattern patron = Pattern.compile(TEXT_PATTERN);
        if (!patron.matcher(textFieldNumero.getText()).matches()) {
            RegexValidator textValidator = new RegexValidator();
            textValidator.setRegexPattern(TEXT_PATTERN);
            textValidator.setMessage("Campo invalido");
            textFieldNumero.getValidators().add(textValidator);

            textFieldNumero.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (!newValue) {
                    textFieldNumero.validate();
                }
            }));
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        final String TEXT_PATTERN = "^{8,20}";

        Pattern patron = Pattern.compile(TEXT_PATTERN);
        if (!patron.matcher(textFieldContrasenia.getText()).matches()) {
            RegexValidator textValidator = new RegexValidator();
            textValidator.setRegexPattern(TEXT_PATTERN);
            textValidator.setMessage("La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula.");
            textFieldContrasenia.getValidators().add(textValidator);

            textFieldContrasenia.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (!newValue) {
                    textFieldContrasenia.validate();
                }
            }));
            return false;
        } else {
            return true;
        }
    }

}
