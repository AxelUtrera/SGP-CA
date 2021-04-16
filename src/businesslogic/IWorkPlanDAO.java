package businesslogic;

import domain.Strategy;
import domain.Target;
import domain.WorkPlan;

import java.util.List;

public interface IWorkPlanDAO {

    public List<WorkPlan> displayAllWorkPlans();
    public int addWorkPlan(WorkPlan workPlan);
    public int updateOneWorkPlan(WorkPlan workPlan);
    public int deleteOneWorkPlan(String workPlanKey);
    public List<Target> displayAllWorkPlanTargets(String workPlanKey);
    public WorkPlan foundWorkPlanByKey(String workPlanKey);

}
