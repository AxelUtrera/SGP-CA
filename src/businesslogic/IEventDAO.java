package businesslogic;

import domain.Event;

import java.util.List;

public interface IEventDAO {

    public boolean addEvent(Event event) throws BusinessLogicException;
    public List<Event> displayAllEvents() throws BusinessLogicException;
    public Event findEventById(String idEvent) throws BusinessLogicException;
    public boolean updateEventById(Event event) throws BusinessLogicException;
    public boolean deleteEventById(String idAcademicEvent) throws BusinessLogicException;
}
