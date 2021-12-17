package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class AlertInterface {

    public void openAlertWarningIncompleteData(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Campos invalidos");
        alert.setHeaderText("Existen campos invalidos");
        alert.setContentText("Llene los campos correctamente e intentelo de nuevo");
        alert.show();
    }

    public void openAlertSuccesfulInsert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Registro Exitoso");
        alert.setContentText("Su registro se ha realizado con éxito!");
        alert.show();
    }

    public void openAlertInvalidData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Campos Invalidos");
        alert.setHeaderText("Campos Invalidos");
        alert.setContentText("Existen campos invalidos en su registro");
        alert.show();
    }
    public void openAlertDuplicatedData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Miembro existente");
        alert.setHeaderText("El miembro ya esta registrado");
        alert.show();
    }

    public Optional<ButtonType> openConfirmationRegisterData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de registro");
        alert.setContentText("¿Está seguro/a de hacer el registro?");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
    }

    public void openAlertFailedInsert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo conectar a la base de datos");
        alert.show();
    }

    public void openAlertSuccesfulUpdate(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Actualización exitosa");
        alert.setContentText("Su actualización se ha realizado con éxito!");
        alert.show();
    }

    public void openAlertInvalidDate(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Campos Invalidos");
        alert.setHeaderText("Campos Invalidos");
        alert.setContentText("Asegurate  de que la fecha sea correcta y valida");
        alert.show();
    }

}
