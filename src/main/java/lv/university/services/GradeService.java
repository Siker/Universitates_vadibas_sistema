package lv.university.services;

import lv.university.entities.Course;
import lv.university.entities.Grade;
import lv.university.entities.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GradeService {
    private final List<Grade> grades = new ArrayList<>();
    private int nextId = 1;

    public Grade addGrade(Student student, Course course, double value, String comment) {
        Grade grade = new Grade(nextId++, student, course, value, LocalDate.now(), comment);
        grades.add(grade);
        student.addGrade(grade);
        return grade;
    }

    public List<Grade> getGradesByStudent(Student student) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudent().getId() == student.getId()) {
                result.add(g);
            }
        }
        return result;
    }

    public List<Grade> getGradesByCourse(Course course) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getCourse().getId() == course.getId()) {
                result.add(g);
            }
        }
        return result;
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }
}
