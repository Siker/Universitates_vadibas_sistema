package lv.university.repositories;

import lv.university.entities.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherRepository {
    private final List<Teacher> teachers = new ArrayList<>();
    private int nextId = 1;

    public Teacher save(Teacher teacher) {
        if (teacher.getId() == 0) {
            teacher.setId(nextId++);
        }
        if (findById(teacher.getId()).isEmpty()) {
            teachers.add(teacher);
        }
        return teacher;
    }

    public Optional<Teacher> findById(int id) {
        return teachers.stream().filter(t -> t.getId() == id).findFirst();
    }

    public Optional<Teacher> findByEmail(String email) {
        return teachers.stream().filter(t -> t.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public List<Teacher> findAll() {
        return new ArrayList<>(teachers);
    }

    public boolean delete(int id) {
        return teachers.removeIf(t -> t.getId() == id);
    }

    public int getNextId() {
        return nextId;
    }
}
