package businesslogic;

import domain.TargetStrategy;

import java.util.List;

public interface ITargetStrategyDAO {

    public List<TargetStrategy> displayAllTargetStrategies();
    public void insertOneTargetStrategy(TargetStrategy targetStrategy);
    public void updateOneTargetStrategy(TargetStrategy targetStrategy);
    public void deleteOneTargetStrategy(TargetStrategy targetStrategy);

}
