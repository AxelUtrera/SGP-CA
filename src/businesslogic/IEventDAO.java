package businesslogic;

import domain.Event;
import java.util.List;

public interface IEventDAO {

    public void insertOneEvent(Event event);
    public List<Event> displayAllEvents();
    public boolean foundEventById(String idEvent);

}
