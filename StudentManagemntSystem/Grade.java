package assisgnmnetv2;



public class Grade {
    private Student student;
    private Course course;
    private double score;
    public static int counter = 1;

    public Grade(Student student, Course course, double score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getScore() { return score; }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
