package exampleservice;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import exampleservice.Constant.Gender;
import exampleservice.domain.Student;

public class StudentTest {

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

    @Test
    public void Student의_Setter의_메소드_생성을_검증한다() throws SecurityException, NoSuchMethodException{
        String name = "하... 인생이 길다 길어";
        int age = 22;
        Gender gender = Gender.FEMALE;
        Student student = new Student(name, age, gender);

        Method method = student.getClass().getMethod("setName", String.class);
        assertTrue(Objects.nonNull(method));
    }

    @Test
    public void Student의_Setter_Getter_동작을_검증한다() throws SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException , InvocationTargetException {
        String name = "오늘도 시간이 안갑니다";
        int age = 22;
        Gender gender = Gender.FEMALE;
        Student student = new Student(name, age, gender);

        //setter Method 생성
        Method setterMethod = student.getClass().getMethod("setName", String.class);

        String changeName = "하하 녀석";
        setterMethod.invoke(student,changeName);

        //getter Method 검증
        Method getterMethod = student.getClass().getMethod("getName");
        Object getName = getterMethod.invoke(student, (Object[])null);

        assertEquals(getName, changeName);
        
    }
}
