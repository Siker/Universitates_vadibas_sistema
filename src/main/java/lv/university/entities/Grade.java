package lv.university.entities;

import java.time.LocalDate;

public class Grade {
    private int id;
    private Student student;
    private Course course;
    private double value;
    private LocalDate date;
    private String comment;

    public Grade(int id, Student student, Course course, double value, LocalDate date, String comment) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.value = value;
        this.date = date;
        this.comment = comment;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Kurss: " + course.getName() + ", Vērtējums: " + value
                + ", Datums: " + date + ", Komentārs: " + comment;
    }
}
