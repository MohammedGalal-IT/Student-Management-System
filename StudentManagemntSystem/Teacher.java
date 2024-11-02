package assisgnmnetv2;

import java.util.ArrayList;

public class Teacher {
    private int teacherId;
    private String name;
    private String specialty;
    private ArrayList<Course> courses;
    public static int counter = 1;

    public Teacher(int teacherId, String name, String specialty) {
        this.teacherId = teacherId;
        this.name = name;
        this.specialty = specialty;
        this.courses = new ArrayList<>();
    }

    public int getTacherId() { return teacherId; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public ArrayList<Course> getCourses() { return courses; }

    public void setName(String name) {
        this.name = name;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
