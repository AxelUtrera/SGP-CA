package businesslogic;

import domain.Prototype;

import java.util.List;


public interface IPrototypeDAO {

    public List<Prototype> displayAllPrototypes() throws BusinessLogicException;
    public boolean addPrototype(Prototype prototype) throws BusinessLogicException;
    public boolean updatePrototype(Prototype prototype) throws BusinessLogicException;
    public boolean deletePrototype(String idPrototype) throws BusinessLogicException;
    public Prototype foundPrototypeById(String idprototype) throws BusinessLogicException;
    public int getLastId() throws BusinessLogicException;

}
