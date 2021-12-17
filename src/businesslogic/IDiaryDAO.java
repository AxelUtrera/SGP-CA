package businesslogic;

import domain.Diary;

import java.util.List;

public interface IDiaryDAO {

    public boolean addDiary(Diary diary);
    public List<Diary> displayAllDiaries();
    public Diary findDiaryById(String idMeetFind);
    public boolean updateDiaryById(Diary diary);
    public boolean deleteDiaryById(String idMeet);

}
