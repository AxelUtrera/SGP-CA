package businesslogic;

import domain.WorkPlanTarget;

import java.util.List;

public interface IWorkPlanTargetDAO {

    public List<WorkPlanTarget> displayAllWorkPlanTargets();
    public void insertOneWorkPlanTarget(WorkPlanTarget workPlanTarget);
    public void updateOneWorkPlanTarget(WorkPlanTarget workPlanTarget);
    public void deleteOneWorkPlanTarget(WorkPlanTarget workPlanTarget);

}
