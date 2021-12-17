package businesslogic;

import domain.Memorandum;

import java.util.List;

public interface IMemorandumDAO {

    public boolean addMemorandum(Memorandum memorandum);
    public List<Memorandum> displayAllMemorandums();
    public Memorandum findMemorandumById(String idMemorandum);
    public boolean deleteMemorandumById(String idMemorandum);

}
