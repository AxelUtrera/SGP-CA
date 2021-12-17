package businesslogic;


import domain.InvestigationProject;

import java.util.List;

public interface IInvestigationProjectDAO {

    public List<InvestigationProject> displayAllinvestigationProjects() throws BusinessLogicException;
    public boolean addInvestigationProject(InvestigationProject investigationProject) throws BusinessLogicException;
    public boolean updateInvestigationProject(InvestigationProject investigationProject) throws BusinessLogicException;
    public boolean deleteInvestigationProject(String idInvestigationProject) throws BusinessLogicException;
    public InvestigationProject foundInvestigationProjectByidInvestigationProject(String idInvestigationproject) throws BusinessLogicException;
    public InvestigationProject foundInvestigationProjectByTitle(String titlee) throws BusinessLogicException;
    public int getLastIdInvestigationProject() throws BusinessLogicException;

}
