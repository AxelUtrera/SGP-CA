package businesslogic;

import domain.Strategy;

import java.util.List;

public interface IStrategyDAO {

    public List<Strategy> displayAllStrategies();
    public int addStrategy(Strategy strategy);
    public int deleteOneStrategy(String idStrategy);
    public Strategy foundStrategyById(String idStrategy);

}
