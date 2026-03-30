package lv.university.services;

import lv.university.entities.Course;
import lv.university.entities.Student;
import lv.university.entities.Teacher;
import lv.university.repositories.CourseRepository;

import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(String name, String description, Teacher teacher) {
        Course course = new Course(0, name, description, teacher);
        return courseRepository.save(course);
    }

    public boolean removeCourse(int id) {
        return courseRepository.delete(id);
    }

    public List<Student> getEnrolledStudents(int courseId) {
        return courseRepository.findById(courseId)
                .map(Course::getEnrolledStudents)
                .orElse(List.of());
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(int id) {
        return courseRepository.findById(id);
    }
}
