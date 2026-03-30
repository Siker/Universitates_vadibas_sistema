package lv.university.repositories;

import lv.university.entities.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {
    private final List<Course> courses = new ArrayList<>();
    private int nextId = 1;

    public Course save(Course course) {
        if (course.getId() == 0) {
            course.setId(nextId++);
        }
        if (findById(course.getId()).isEmpty()) {
            courses.add(course);
        }
        return course;
    }

    public Optional<Course> findById(int id) {
        return courses.stream().filter(c -> c.getId() == id).findFirst();
    }

    public Optional<Course> findByName(String name) {
        return courses.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public boolean delete(int id) {
        return courses.removeIf(c -> c.getId() == id);
    }

    public int getNextId() {
        return nextId;
    }
}
