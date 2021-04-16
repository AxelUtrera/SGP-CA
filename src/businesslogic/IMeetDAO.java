package businesslogic;

import domain.Meet;
import java.util.List;

public interface IMeetDAO {

    public void insertOneMeet(Meet meet);
    public List<Meet> displayAllMeets();
    public int foundMeetById(String idMeet);

}
