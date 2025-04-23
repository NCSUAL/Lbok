package exampleservice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import exampleservice.Constant.Gender;
import exampleservice.domain.Student;

public class StudentTest {

    @Test
    public void Student의_Getter가_제대로_작동하는지_검증한다(){

        String name = "퉁 퉁 퉁 퉁 퉁 퉁 퉁 퉁 퉁 사후르";
        int age = 58000;
        Gender gender = Gender.TUNG;
        
        Student student = new Student(name,age,gender);

        //test1 getName 동작 검증
        assertTrue(student.getName().contains("퉁"));
        assertTrue(student.getName().contains("사후르"));
        
        //test2 getAge 동작 검증
        assertEquals(student.getAge(), 58000);
        
        //test3 getGender 동작 검증
        assertEquals(student.getGender(), Gender.TUNG);

    }
}
