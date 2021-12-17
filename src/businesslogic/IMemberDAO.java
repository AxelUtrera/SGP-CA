package businesslogic;


import domain.Member;
import javafx.collections.ObservableList;
import java.util.List;

public interface IMemberDAO {

    public List<Member> displayAllMembers() throws BusinessLogicException;
    public boolean addMember(Member member) throws BusinessLogicException;
    public boolean updateMember(Member member) throws BusinessLogicException;
    public boolean deleteMember(String curp) throws BusinessLogicException;
    public Member findMemberByCurp(String curp) throws BusinessLogicException;
    public Member findMemberByName(String nameFind) throws BusinessLogicException;
    public ObservableList<String> parseToObservableList();
    public Member findMemberByInstitutionalMail(String institutionalMail) throws BusinessLogicException;
    public String decryptPassword(String institutionalMail) throws BusinessLogicException;
}
