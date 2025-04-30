package exampleservice;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //getName, getAge 생성여부를 판단한다
        Method[] methods = student.getClass().getDeclaredMethods();

        List<String> methodNames  = new ArrayList<String>();

        Arrays.stream(methods).forEach(method -> methodNames.add(method.getName()));

        assertTrue(methodNames.contains("getName"));
        assertTrue(methodNames.contains("getAge"));
    }

    @Test
    public void Student의_Getter의_메소드_생성을_검증한다(){
        String name = "트랄랄레로 트랄랄라";
        int age = 3000;
        Gender gender = Gender.MALE;
        
        Student student = new Student(name,age,gender);
        
        Class<? extends Student> classInstance = student.getClass();

        List<String> methodName = new ArrayList<>();

        Method[] methods = classInstance.getDeclaredMethods();
    
        for(Method method : methods){
            methodName.add(method.getName());
        }  

        assertTrue(methodName.contains("getAge"));
        assertFalse(methodName.contains("getGender"));
    }
}
