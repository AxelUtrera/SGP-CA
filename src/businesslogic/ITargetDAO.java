package businesslogic;

import domain.Target;
import java.util.List;

public interface ITargetDAO {

    public boolean addTarget(Target target) throws BusinessLogicException;
    public int getLastIdTargetNumber() throws BusinessLogicException;
    public boolean updateTarget(Target target) throws BusinessLogicException;
    public Target foundTargetByIdTarget(String idTarget) throws BusinessLogicException;
    public List<Target> foundTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException;
    public List<Target> foundPendingTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException;
    public List<Target> foundAchievedTargetsByKeyWorkplan(String keyWorkplan) throws BusinessLogicException;

}
