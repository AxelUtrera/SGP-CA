package businesslogic;

import domain.AcademicGroup;
import java.util.List;

public interface IAcademicGroupDAO {

    public List<AcademicGroup> displayAllAcademicGroups() throws BusinessLogicException;
    public boolean addAcademicGroup(AcademicGroup academicGroup)throws BusinessLogicException;
    public boolean updateAcademicGroup(AcademicGroup academicGroup) throws BusinessLogicException;
    public boolean deleteAcademicGroup(String key) throws BusinessLogicException;
    public AcademicGroup foundAcademicGroupByID(String id) throws BusinessLogicException;
    public AcademicGroup foundAcademicGroupByName(String nameAG) throws BusinessLogicException;
}
