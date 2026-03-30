package lv.university;

import lv.university.repositories.*;
import lv.university.services.*;
import lv.university.ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializācija: repozitoriji
        StudentRepository studentRepository = new StudentRepository();
        TeacherRepository teacherRepository = new TeacherRepository();
        CourseRepository courseRepository = new CourseRepository();

        // Inicializācija: servisi
        AuthService authService = new AuthService(studentRepository, teacherRepository);
        StudentService studentService = new StudentService(studentRepository);
        TeacherService teacherService = new TeacherService(teacherRepository, courseRepository);
        CourseService courseService = new CourseService(courseRepository);
        GradeService gradeService = new GradeService();
        ScheduleService scheduleService = new ScheduleService();

        // Pievienot demonstrācijas datus
        loadDemoData(authService, teacherService, courseService, scheduleService);

        // Inicializācija: UI
        Scanner scanner = new Scanner(System.in);
        StudentMenu studentMenu = new StudentMenu(studentService, courseService,
                gradeService, scheduleService, scanner);
        MainMenu mainMenu = new MainMenu(authService, studentService, teacherService,
                courseService, gradeService, scheduleService, studentMenu, scanner);

        // Palaist programmu
        mainMenu.start();

        scanner.close();
    }

    private static void loadDemoData(AuthService authService, TeacherService teacherService,
                                     CourseService courseService, ScheduleService scheduleService) {
        // Reģistrēt demonstrācijas pasniedzēju
        authService.registerTeacher("Anna Bērziņa", "anna@university.lv", "parole123");

        // Reģistrēt demonstrācijas studentu
        authService.registerStudent("Jānis Kalniņš", "janis@university.lv", "parole123");

        // Izveidot demonstrācijas kursus
        var course1 = teacherService.createCourse(1, "Matemātika", "Augstākā matemātika I");
        var course2 = teacherService.createCourse(1, "Programmēšana", "Java programmēšanas pamati");

        // Pievienot grafiku
        if (course1 != null) {
            scheduleService.addSchedule(course1, "Pirmdiena", "09:00", "10:30", "A101");
            scheduleService.addSchedule(course1, "Trešdiena", "09:00", "10:30", "A101");
        }
        if (course2 != null) {
            scheduleService.addSchedule(course2, "Otrdiena", "11:00", "12:30", "B205");
            scheduleService.addSchedule(course2, "Ceturtdiena", "11:00", "12:30", "B205");
        }

        System.out.println("Demonstrācijas dati ielādēti.");
        System.out.println("Pasniedzējs: anna@university.lv / parole123");
        System.out.println("Students: janis@university.lv / parole123");
    }
}
