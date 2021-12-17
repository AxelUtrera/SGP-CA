package testpackages.testbusinesslogic;

import businesslogic.BusinessLogicException;
import businesslogic.MemberDAO;
import controller.AlertInterface;
import domain.Member;
import org.junit.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MemberTest {

    private Date date =  new Date(2001,01,31);
    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllMembersSuccessfulTest(){ //FAIL

        MemberDAO memberDAO = new MemberDAO();
        List<Member> membersExpected = new ArrayList<>();
        Member memberExpected1 = new Member("MOLK010131MNELPTA0","Katia Sarai Molina Lopez", date, "zs19014027@estudiantes.uv.mx", "228111", "Miembro", "ACG-1", "123");
        Member memberExpected2 = new Member("PECC00016HVZRSHA4","Christopher Perez Castro", date, "zs19032145@estudiantes.uv.mx", "2281342", "Miembro", "ACG-1", "123");
        membersExpected.add(memberExpected1);
        membersExpected.add(memberExpected2);
        try{
            List<Member> membersResult = memberDAO.displayAllMembers();

            assertEquals("Prueba obtener todas los miembros existentes", membersExpected, membersResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllMembersFailedTest(){
        MemberDAO memberDAO = new MemberDAO();
        List<Member> membersExpected = new ArrayList<>();
        try{
            List<Member> membersResult = memberDAO.displayAllMembers();
            assertEquals("Prueba alterna con lista vacia de miembros", membersExpected, membersResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addMemberSuccessfulTest(){

        Member memberExpected = new Member("MOLK010131MNELPTA0","Katia Sarai Molina Lopez", date, "zs19014027@estudiantes.uv.mx", "228111", "Miembro", "ACG-1", "123");
        MemberDAO memberDAO = new MemberDAO();
        try{
            boolean membersAddedResult = memberDAO.addMember(memberExpected);
            assertTrue(membersAddedResult);
        } catch (BusinessLogicException ex) {
            System.out.println("Prueba fallida");
        }
    }
    @Test
    public void addMemberSuccessfulTest2(){

        Member memberExpected = new Member("PECC00016HVZRSHA4","Christopher Perez Castro", date, "zs19032145@estudiantes.uv.mx", "2281342", "Miembro", "ACG-1", "123");
        MemberDAO memberDAO = new MemberDAO();
        try{
            boolean membersAddedResult = memberDAO.addMember(memberExpected);
            assertTrue(membersAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateMemberTest(){

        MemberDAO memberDAO = new MemberDAO();
        String CURP = "MOLK010131MNELPTA0";
        try{
            Member member = memberDAO.findMemberByCurp(CURP);
            member.setName("Rosa Lopez Galicia");
            member.setTelephoneNumber("22456");
            boolean updatedMembersResult = memberDAO.updateMember(member);
            assertTrue(updatedMembersResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteMemberSuccessfulTest(){

        String CURP = "PECC00016HVZRSHA4";
        MemberDAO memberDAO = new MemberDAO();
        try{
            boolean membersDeletedResult = memberDAO.deleteMember(CURP);
            assertTrue(membersDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteMemberFailedTest(){

        String CURP = "MOLK0101MNELPTA2";
        MemberDAO memberDAO = new MemberDAO();
        try{
            boolean membersDeletedResult = memberDAO.deleteMember(CURP);
            assertTrue(membersDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundMemberByCURPSuccessfulTest(){
        String CURP = "MOLK010131MNELPTA0";
        MemberDAO memberDAO = new MemberDAO();
        Member memberExpected = new Member("MOLK010131MNELPTA0","Katia Sarai Molina Lopez", date, "zs19014027@estudiantes.uv.mx", "228111", "Miembro", "ACG-01", "123");
        try{
            Member memberResult = memberDAO.findMemberByCurp(CURP);
            assertEquals("Prueba verificar que un miembro exista", memberExpected.getCurp(), memberResult.getCurp());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundMemberByCURPFailedTest(){
        String CURP = "MOLK0101MNELPTA2";
        MemberDAO memberDAO = new MemberDAO();
        try{
            assertNull("Prueba alterna, no existe el miembro", memberDAO.findMemberByCurp(CURP));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
