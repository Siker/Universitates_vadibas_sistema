package lv.university.repositories;

import lv.university.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public Student save(Student student) {
        if (student.getId() == 0) {
            student.setId(nextId++);
        }
        if (findById(student.getId()).isEmpty()) {
            students.add(student);
        }
        return student;
    }

    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    public Optional<Student> findByEmail(String email) {
        return students.stream().filter(s -> s.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public boolean delete(int id) {
        return students.removeIf(s -> s.getId() == id);
    }

    public int getNextId() {
        return nextId;
    }
}
