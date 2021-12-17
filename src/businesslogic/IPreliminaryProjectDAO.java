package businesslogic;

import domain.PreliminaryProject;

import java.util.List;

public interface IPreliminaryProjectDAO {

    public List<PreliminaryProject> displayAllPreliminaryProjects() throws BusinessLogicException;
    public boolean addPreliminaryProject(PreliminaryProject preliminaryProject) throws BusinessLogicException;
    public int getLastIdNumber(String column, String table) throws BusinessLogicException;
    public boolean updatePreliminaryProject(PreliminaryProject preliminaryProject) throws BusinessLogicException;
    public boolean deletePreliminaryProject(String idPreliminaryProject) throws BusinessLogicException;
    public PreliminaryProject foundPreliminaryProjectById(String idPreliminaryProject) throws BusinessLogicException;
    public List<PreliminaryProject> foundPreliminaryProjectsByIdInvestigationProject(String idInvestigationProject) throws BusinessLogicException;
    public List<String> foundStudentInformationByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException;
    public List<String> foundLgacByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException;
    public List<String> foundCodirectorsByIdPreliminaryProject(String idPreliminaryProject) throws BusinessLogicException;
    public boolean addStudentInformationToPreliminaryProject(String idPreliminaryProject, String studentInformation) throws BusinessLogicException;
    public boolean addCodirectorToPreliminaryProject(String idPreliminaryProject, String codirectorInformation) throws BusinessLogicException;
    public boolean addLgacToPreliminaryProject(String idPreliminaryProject, String lgac) throws BusinessLogicException;
    public boolean deletePreliminaryProjectStudent(String idPreliminaryProject, String studentInformation) throws BusinessLogicException;
    public boolean deletePreliminaryProjectLgac(String idPreliminaryProject, String lgac) throws BusinessLogicException;
    public boolean deletePreliminaryProjectCodirector(String idPreliminaryProject, String codirectorInformation) throws BusinessLogicException;

}
