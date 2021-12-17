package businesslogic;

import domain.ReceptionWork;

import java.util.List;

public interface IReceptionWorkDAO {

    public List<ReceptionWork> displayAllReceptionWorks() throws BusinessLogicException;
    public boolean addReceptionWork(ReceptionWork receptionWork) throws BusinessLogicException;
    public boolean updateReceptionWork(ReceptionWork receptionWork) throws BusinessLogicException;
    public boolean deleteReceptionWork(String idReceptionWork) throws BusinessLogicException;
    public ReceptionWork foundReceptionWorkByID(String idReceptionwork) throws BusinessLogicException;
    public int getLastId() throws BusinessLogicException;

}
