package lv.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private String teacherId;
    private List<Course> teachingCourses;

    public Teacher(int id, String name, String email, String password, String teacherId) {
        super(id, name, email, password, "TEACHER");
        this.teacherId = teacherId;
        this.teachingCourses = new ArrayList<>();
    }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public List<Course> getTeachingCourses() { return teachingCourses; }

    public void addCourse(Course course) {
        if (!teachingCourses.contains(course)) {
            teachingCourses.add(course);
        }
    }

    public void removeCourse(Course course) {
        teachingCourses.remove(course);
    }

    @Override
    public String toString() {
        return super.toString() + ", Pasniedzējs ID: " + teacherId;
    }
}
