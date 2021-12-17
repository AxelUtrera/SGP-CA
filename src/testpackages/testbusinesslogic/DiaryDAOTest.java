package testpackages.testbusinesslogic;

import businesslogic.DiaryDAO;
import domain.Diary;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DiaryDAOTest {

    Diary diaryExpected1 = new Diary(1,"MOLK010131MNELPTA0", 256, 250,"Temas de la semana", "MET-01");
    Diary diaryExpected2 = new Diary(2,"Karen Cortez", 220, 200,"Temas del mes", "MET-02");
    DiaryDAO diaryDAO = new DiaryDAO();

    private void insertDataToDataBase(){
        diaryDAO.addDiary(diaryExpected1);
        diaryDAO.addDiary(diaryExpected2);
    }

    private List<Diary> insertDataToList(){
        List<Diary> diaryList = new ArrayList<>();
        diaryList.add(diaryExpected1);
        diaryList.add(diaryExpected2);
        return diaryList;
    }

    @Test
    public void addDiarySuccessfulTest(){
        boolean diaryResult = diaryDAO.addDiary(diaryExpected1);
        assertTrue(diaryResult);
    }

    @Test
    public void diaplayDiariesSuccessfulTest(){
        insertDataToDataBase();
        List<Diary> diaryExpected = insertDataToList();
        List<Diary> diaryResult = diaryDAO.displayAllDiaries();
        assertEquals("Prueba para mostrar datos de la base de datos", diaryExpected, diaryResult);
    }

    @Test
    public void displayAllDiariesFailedTest(){
        List<Diary> diaryExpected = new ArrayList<>();
        List<Diary> diaryResult = diaryDAO.displayAllDiaries();
        assertEquals("Prueba de muestra de agendas con lista vacia", diaryExpected, diaryResult);
    }

    @Test
    public void findDiaryByIdSuccessfulTest(){
        Diary diaryExpected = diaryExpected1;
        Diary diaryResult = diaryDAO.findDiaryById(diaryExpected1.getIdMeet());
        assertEquals("Prueba de busqueda de diary por id de reunion", diaryExpected, diaryResult);
    }

    @Test
    public void findDiaryByIdFailedTest(){
        Diary diaryResult = diaryDAO.findDiaryById("DIARY-01");
        assertNull("Prueba de busqueda por id, enviando id inexistente", diaryResult);
    }

    @Test
    public void updateDiaryByIdSuccessfulTest(){
        Diary newDiary = new Diary(1,"Angel Juan", 225, 250,"Temas de la semana", "MET-01");
        boolean diaryResult = diaryDAO.updateDiaryById(newDiary);
        assertTrue(diaryResult);
    }

    @Test
    public void updateDiaryByIdFailedTest(){
        Diary newDiary = new Diary(5,"Angel Juan", 225, 250,"Temas de la semana", "MET-01");
        boolean diaryResult = diaryDAO.updateDiaryById(newDiary);
        assertFalse(diaryResult);
    }

    @Test
    public void deleteDiaryByIdSuccessfulTest(){
        boolean diaryResult = diaryDAO.deleteDiaryById(diaryExpected1.getIdMeet());
        diaryDAO.deleteDiaryById(diaryExpected2.getIdMeet());
        assertTrue(diaryResult);
    }

    @Test
    public void deleteDiaryByIdFailedTest(){
        boolean diaryResult = diaryDAO.deleteDiaryById("MEET-01");
        assertFalse(diaryResult);
    }

    @Test
    public void getMaxNumberOfDiaries(){
        int diaryExpected = 12;
        int diaryResult = diaryDAO.getMaxNumberOfDiaries();
        assertEquals("Si los numeros son iguales el test es correcto", diaryExpected, diaryResult);
    }
}
