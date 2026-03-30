package lv.university.ui;

import lv.university.entities.*;
import lv.university.services.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentMenu {
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final ScheduleService scheduleService;
    private final Scanner scanner;

    public StudentMenu(StudentService studentService, CourseService courseService,
                       GradeService gradeService, ScheduleService scheduleService,
                       Scanner scanner) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.scheduleService = scheduleService;
        this.scanner = scanner;
    }

    public void show(Student student) {
        boolean running = true;
        while (running) {
            System.out.println("\n========== STUDENTA IZVĒLNE ==========");
            System.out.println("Sveiks, " + student.getName() + "! (" + student.getStudentId() + ")");
            System.out.println("1. Skatīt visus kursus");
            System.out.println("2. Reģistrēties kursam");
            System.out.println("3. Atreģistrēties no kursa");
            System.out.println("4. Skatīt manus kursus");
            System.out.println("5. Skatīt manas atzīmes");
            System.out.println("6. Skatīt kursa grafiku");
            System.out.println("0. Iziet");
            System.out.print("Izvēle: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listAllCourses();
                case "2" -> enrollInCourse(student);
                case "3" -> unenrollFromCourse(student);
                case "4" -> listMyCourses(student);
                case "5" -> viewMyGrades(student);
                case "6" -> viewCourseSchedule();
                case "0" -> running = false;
                default -> System.out.println("Nepareiza izvēle. Mēģiniet vēlreiz.");
            }
        }
    }

    private void listAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("Nav pieejamu kursu.");
            return;
        }
        System.out.println("\n--- Pieejamie kursi ---");
        courses.forEach(System.out::println);
    }

    private void enrollInCourse(Student student) {
        listAllCourses();
        System.out.print("Ievadiet kursa ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Course> optCourse = courseService.findById(courseId);
            if (optCourse.isEmpty()) {
                System.out.println("Kurss ar šādu ID nav atrasts.");
                return;
            }
            boolean success = studentService.enrollInCourse(student.getId(), optCourse.get());
            if (success) {
                System.out.println("Veiksmīgi reģistrēts kursam: " + optCourse.get().getName());
            } else {
                System.out.println("Reģistrācija neizdevās.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }

    private void unenrollFromCourse(Student student) {
        listMyCourses(student);
        System.out.print("Ievadiet kursa ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Course> optCourse = courseService.findById(courseId);
            if (optCourse.isEmpty()) {
                System.out.println("Kurss ar šādu ID nav atrasts.");
                return;
            }
            boolean success = studentService.unenrollFromCourse(student.getId(), optCourse.get());
            if (success) {
                System.out.println("Veiksmīgi atreģistrēts no kursa: " + optCourse.get().getName());
            } else {
                System.out.println("Atreģistrācija neizdevās.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }

    private void listMyCourses(Student student) {
        List<Course> courses = student.getEnrolledCourses();
        if (courses.isEmpty()) {
            System.out.println("Nav reģistrētu kursu.");
            return;
        }
        System.out.println("\n--- Mani kursi ---");
        courses.forEach(System.out::println);
    }

    private void viewMyGrades(Student student) {
        List<Grade> grades = gradeService.getGradesByStudent(student);
        if (grades.isEmpty()) {
            System.out.println("Nav atzīmju.");
            return;
        }
        System.out.println("\n--- Manas atzīmes ---");
        grades.forEach(System.out::println);
    }

    private void viewCourseSchedule() {
        listAllCourses();
        System.out.print("Ievadiet kursa ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Course> optCourse = courseService.findById(courseId);
            if (optCourse.isEmpty()) {
                System.out.println("Kurss ar šādu ID nav atrasts.");
                return;
            }
            List<Schedule> scheduleList = scheduleService.getScheduleByCourse(optCourse.get());
            if (scheduleList.isEmpty()) {
                System.out.println("Nav grafika šim kursam.");
                return;
            }
            System.out.println("\n--- Grafiks: " + optCourse.get().getName() + " ---");
            scheduleList.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }
}
