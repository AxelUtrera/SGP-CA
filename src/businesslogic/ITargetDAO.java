package businesslogic;

import domain.Strategy;
import domain.Target;

import java.util.List;

public interface ITargetDAO {

    public List<Target> displayAllTargets();
    public int addTarget(Target target);
    public int updateOneTarget(Target target);
    public int deleteOneTarget(String idTarget);
    public List<Strategy> displayAllTargetStrategies(String idTarget);
    public Target foundTargetById(String idTarget);

}
