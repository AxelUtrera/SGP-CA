package controller;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import businesslogic.PreliminaryProjectDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.InvestigationProject;
import domain.PreliminaryProject;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.List;

public class FXMLConsultPreliminaryProjectController{

    private final PreliminaryProjectDAO PRELIMINARYPROJECT_DAO = new PreliminaryProjectDAO();
    private final InvestigationProjectDAO INVESTIGATIONPROJECT_DAO = new InvestigationProjectDAO();
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @FXML
    private JFXListView<String> listViewCodirectors;
    @FXML
    private JFXListView<String> listViewStudents;
    @FXML
    private JFXListView<String> listViewLgac;
    @FXML
    private JFXTextField textFieldTitle;
    @FXML
    private JFXTextField textFieldStartDate;
    @FXML
    private JFXTextField textFieldDuration;
    @FXML
    private JFXTextField textFieldStatus;
    @FXML
    private JFXTextField textFieldInvestigationProject;
    @FXML
    private JFXTextField textFieldModality;
    @FXML
    private JFXTextArea textAreaDescription;
    @FXML
    private JFXButton buttonExitConsult;

    public void setPreliminaryProjectInformation(String idPreliminaryProject){

        try {
            PreliminaryProject preliminaryProject = PRELIMINARYPROJECT_DAO.foundPreliminaryProjectById(idPreliminaryProject);

            textFieldTitle.setText(preliminaryProject.getTitle());
            textFieldDuration.setText(String.valueOf(preliminaryProject.getDuration()));
            textFieldModality.setText(preliminaryProject.getModality());
            textAreaDescription.setText(preliminaryProject.getDescription());
            textFieldStartDate.setText(String.valueOf(preliminaryProject.getStartDate()));
            textFieldStatus.setText(preliminaryProject.getStatus());
            if (preliminaryProject.getIdInvestigationProject() != null) {
                InvestigationProject investigationProject = INVESTIGATIONPROJECT_DAO.foundInvestigationProjectByidInvestigationProject(preliminaryProject.getIdInvestigationProject());
                String investigationProjectInformation = investigationProject.getIdInvestigationProject() + " [" + investigationProject.getTitle() + "]";
                textFieldInvestigationProject.setText(investigationProjectInformation);
            } else {
                textFieldInvestigationProject.setText("Ninguno");
            }

            List<String> studentsInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundStudentInformationByIdPreliminaryProject(idPreliminaryProject);
            listViewStudents.getItems().addAll(studentsInformationRecoveredList);

            List<String> codirectosInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundCodirectorsByIdPreliminaryProject(idPreliminaryProject);
            listViewCodirectors.getItems().addAll(codirectosInformationRecoveredList);

            List<String> lgacInformationRecoveredList = PRELIMINARYPROJECT_DAO.foundLgacByIdPreliminaryProject(idPreliminaryProject);
            listViewLgac.getItems().addAll(lgacInformationRecoveredList);
        }catch(BusinessLogicException ex){
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    public void exitConsultPreliminaryProject(){

        Stage stage = (Stage) buttonExitConsult.getScene().getWindow();
        stage.setTitle("SGP-CA");
        stage.close();

    }

}
