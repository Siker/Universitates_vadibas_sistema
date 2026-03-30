package lv.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String studentId;
    private List<Course> enrolledCourses;
    private List<Grade> grades;

    public Student(int id, String name, String email, String password, String studentId) {
        super(id, name, email, password, "STUDENT");
        this.studentId = studentId;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public List<Course> getEnrolledCourses() { return enrolledCourses; }

    public List<Grade> getGrades() { return grades; }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    @Override
    public String toString() {
        return super.toString() + ", Students ID: " + studentId;
    }
}
