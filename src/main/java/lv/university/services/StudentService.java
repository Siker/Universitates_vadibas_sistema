package lv.university.services;

import lv.university.entities.Course;
import lv.university.entities.Grade;
import lv.university.entities.Student;
import lv.university.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(String name, String email, String password) {
        if (studentRepository.findByEmail(email).isPresent()) {
            return null;
        }
        int id = studentRepository.getNextId();
        String studentId = "S" + String.format("%04d", id);
        Student student = new Student(0, name, email, password, studentId);
        return studentRepository.save(student);
    }

    public boolean removeStudent(int id) {
        return studentRepository.delete(id);
    }

    public boolean enrollInCourse(int studentId, Course course) {
        Optional<Student> optStudent = studentRepository.findById(studentId);
        if (optStudent.isEmpty()) {
            return false;
        }
        Student student = optStudent.get();
        student.enrollInCourse(course);
        course.addStudent(student);
        return true;
    }

    public boolean unenrollFromCourse(int studentId, Course course) {
        Optional<Student> optStudent = studentRepository.findById(studentId);
        if (optStudent.isEmpty()) {
            return false;
        }
        Student student = optStudent.get();
        student.unenrollFromCourse(course);
        course.removeStudent(student);
        return true;
    }

    public List<Grade> getGrades(int studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getGrades)
                .orElse(List.of());
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(int id) {
        return studentRepository.findById(id);
    }
}
