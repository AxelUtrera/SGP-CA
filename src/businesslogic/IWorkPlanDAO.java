package businesslogic;

import domain.WorkPlan;
import java.util.List;

public interface IWorkPlanDAO {

    public List<WorkPlan> displayAllWorkPlans() throws BusinessLogicException;
    public boolean addWorkPlan(WorkPlan workPlan) throws BusinessLogicException;
    public boolean updateWorkPlan(WorkPlan workPlan, String originalKeyWorkPlan) throws BusinessLogicException;
    public boolean deleteWorkPlan(String keyWorkPlan) throws BusinessLogicException;
    public WorkPlan foundWorkPlanByKeyWorkPlan(String keyWorkPlan) throws BusinessLogicException;

}
