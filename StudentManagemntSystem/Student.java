package assisgnmnetv2;

import java.util.ArrayList;
import java.util.Date;

public class Student {
    private int studentId;
    private String name;
    private int age;
    //private Date birthDate;
    private String gender;
    //private boolean encrollmentStatus;
    private ArrayList<Grade> grades;
    public static int counter = 1;

    public Student(int studentId, String name, int age,/*Date birthDate,*/  String gender/*, boolean encrollmentStatus*/ ) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        //this.birthDate = birthDate;
        this.gender = gender;
        //this.encrollmentStatus = encrollmentStatus;
        this.grades = new ArrayList<>();
    }

    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public ArrayList<Grade> getGrades() { return grades; }

//    public Date getBirthDate() {
//        return birthDate;
//    }

//    public boolean isEncrollmentStatus() {
//        return encrollmentStatus;
//    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
}