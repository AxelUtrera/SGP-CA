package businesslogic;

import domain.Meet;

import java.util.List;

public interface IMeetDAO {

    public boolean addMeet(Meet meet);
    public List<Meet> displayAllMeets();
    public Meet findMeetById(String idMeet);
    public boolean updateMeetById(Meet meet);
    public boolean deleteMeetById(String idMeetToDelete);
}
