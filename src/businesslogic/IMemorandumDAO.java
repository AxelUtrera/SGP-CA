package businesslogic;

import domain.Memorandum;
import java.util.List;

public interface IMemorandumDAO {

    public List<Memorandum> displayAllMemorandums();
    public int foundMemorandumById(String idMemorandum);

}
