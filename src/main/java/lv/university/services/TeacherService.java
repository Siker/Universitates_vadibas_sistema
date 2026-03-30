package lv.university.services;

import lv.university.entities.Course;
import lv.university.entities.Teacher;
import lv.university.repositories.CourseRepository;
import lv.university.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public Teacher addTeacher(String name, String email, String password) {
        if (teacherRepository.findByEmail(email).isPresent()) {
            return null;
        }
        int id = teacherRepository.getNextId();
        String teacherId = "T" + String.format("%04d", id);
        Teacher teacher = new Teacher(0, name, email, password, teacherId);
        return teacherRepository.save(teacher);
    }

    public boolean removeTeacher(int id) {
        return teacherRepository.delete(id);
    }

    public Course createCourse(int teacherId, String name, String description) {
        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
        if (optTeacher.isEmpty()) {
            return null;
        }
        Teacher teacher = optTeacher.get();
        Course course = new Course(0, name, description, teacher);
        courseRepository.save(course);
        teacher.addCourse(course);
        return course;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findById(int id) {
        return teacherRepository.findById(id);
    }
}
