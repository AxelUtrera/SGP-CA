package businesslogic;

import domain.Strategy;
import java.util.List;

public interface IStrategyDAO {

    public List<Strategy> displayAllStrategies() throws BusinessLogicException;
    public boolean addStrategy(Strategy strategy) throws BusinessLogicException;
    public boolean deleteStrategy(String idStrategy) throws BusinessLogicException;
    public Strategy foundStrategyByIdStrategy(String idStrategy) throws BusinessLogicException;
    public int getLastIdStrategyNumber() throws BusinessLogicException;
    public List<Strategy> foundStrategiesByIdTarget(String idTarget) throws BusinessLogicException;

}
