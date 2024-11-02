package assisgnmnetv2;

import java.util.ArrayList;

public class Course {
    private int courseId;
    private String name;
    private Teacher teacher;
    private ArrayList<Student> students;
    public static int counter = 1;

    public Course(int courseId, String name, Teacher teacher) {
        this.courseId = courseId;
        this.name = name;
        this.teacher = teacher;
        this.students = new ArrayList<>();
    }

    public int getCourseId() { return courseId; }
    public String getName() { return name; }
    public Teacher getTeacher() { return teacher; }
    public ArrayList<Student> getStudents() { return students; }

    public void setName(String name) {
        this.name = name;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
