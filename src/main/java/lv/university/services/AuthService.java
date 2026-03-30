package lv.university.services;

import lv.university.entities.Person;
import lv.university.entities.Student;
import lv.university.entities.Teacher;
import lv.university.repositories.StudentRepository;
import lv.university.repositories.TeacherRepository;

import java.util.Optional;

public class AuthService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public AuthService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Optional<Person> login(String email, String password) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent() && student.get().getPassword().equals(password)) {
            return Optional.of(student.get());
        }

        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        if (teacher.isPresent() && teacher.get().getPassword().equals(password)) {
            return Optional.of(teacher.get());
        }

        return Optional.empty();
    }

    public boolean registerStudent(String name, String email, String password) {
        if (studentRepository.findByEmail(email).isPresent()
                || teacherRepository.findByEmail(email).isPresent()) {
            return false;
        }
        int id = studentRepository.getNextId();
        String studentId = "S" + String.format("%04d", id);
        Student student = new Student(0, name, email, password, studentId);
        studentRepository.save(student);
        return true;
    }

    public boolean registerTeacher(String name, String email, String password) {
        if (studentRepository.findByEmail(email).isPresent()
                || teacherRepository.findByEmail(email).isPresent()) {
            return false;
        }
        int id = teacherRepository.getNextId();
        String teacherId = "T" + String.format("%04d", id);
        Teacher teacher = new Teacher(0, name, email, password, teacherId);
        teacherRepository.save(teacher);
        return true;
    }
}
