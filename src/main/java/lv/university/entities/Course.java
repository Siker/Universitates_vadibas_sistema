package lv.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String name;
    private String description;
    private Teacher teacher;
    private List<Student> enrolledStudents;
    private List<Schedule> schedules;

    public Course(int id, String name, String description, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.enrolledStudents = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public List<Student> getEnrolledStudents() { return enrolledStudents; }

    public List<Schedule> getSchedules() { return schedules; }

    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    @Override
    public String toString() {
        String teacherName = (teacher != null) ? teacher.getName() : "Nav norādīts";
        return "ID: " + id + ", Nosaukums: " + name + ", Apraksts: " + description
                + ", Pasniedzējs: " + teacherName;
    }
}
