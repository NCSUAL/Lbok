package exampleservice.domain;

import exampleservice.Constant.Gender;
import getter.Getter;

@Getter
public class Student {
    private String name;
    private Integer age;
    private Gender gender;

    public Student(final String name, final Integer age, final Gender gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

}
