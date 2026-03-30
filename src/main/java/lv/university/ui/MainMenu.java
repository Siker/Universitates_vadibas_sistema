package lv.university.ui;

import lv.university.entities.*;
import lv.university.repositories.StudentRepository;
import lv.university.services.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu {
    private final AuthService authService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final ScheduleService scheduleService;
    private final StudentMenu studentMenu;
    private final Scanner scanner;

    public MainMenu(AuthService authService, StudentService studentService,
                    TeacherService teacherService, CourseService courseService,
                    GradeService gradeService, ScheduleService scheduleService,
                    StudentMenu studentMenu, Scanner scanner) {
        this.authService = authService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.scheduleService = scheduleService;
        this.studentMenu = studentMenu;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("==========================================");
        System.out.println("  UNIVERSITĀTES VADĪBAS SISTĒMA");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Pieslēgties");
            System.out.println("2. Reģistrēties");
            System.out.println("0. Iziet");
            System.out.print("Izvēle: ");

            String choice;
            try {
                choice = scanner.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                break;
            }
            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "0" -> {
                    running = false;
                    System.out.println("Uz redzēšanos!");
                }
                default -> System.out.println("Nepareiza izvēle. Mēģiniet vēlreiz.");
            }
        }
    }

    private void login() {
        System.out.print("E-pasts: ");
        String email = scanner.nextLine().trim();
        System.out.print("Parole: ");
        String password = scanner.nextLine().trim();

        Optional<Person> person = authService.login(email, password);
        if (person.isEmpty()) {
            System.out.println("Nepareizs e-pasts vai parole.");
            return;
        }

        Person loggedIn = person.get();
        System.out.println("Sveicināts, " + loggedIn.getName() + "!");

        if ("STUDENT".equals(loggedIn.getRole())) {
            studentMenu.show((Student) loggedIn);
        } else if ("TEACHER".equals(loggedIn.getRole())) {
            showTeacherMenu((Teacher) loggedIn);
        }
    }

    private void register() {
        System.out.println("\n--- Reģistrācija ---");
        System.out.println("1. Reģistrēties kā students");
        System.out.println("2. Reģistrēties kā pasniedzējs");
        System.out.print("Izvēle: ");

        String choice = scanner.nextLine().trim();
        System.out.print("Vārds Uzvārds: ");
        String name = scanner.nextLine().trim();
        System.out.print("E-pasts: ");
        String email = scanner.nextLine().trim();
        System.out.print("Parole: ");
        String password = scanner.nextLine().trim();

        boolean success = false;
        if ("1".equals(choice)) {
            success = authService.registerStudent(name, email, password);
        } else if ("2".equals(choice)) {
            success = authService.registerTeacher(name, email, password);
        } else {
            System.out.println("Nepareiza izvēle.");
            return;
        }

        if (success) {
            System.out.println("Reģistrācija veiksmīga! Tagad varat pieslēgties.");
        } else {
            System.out.println("Reģistrācija neizdevās. Šāds e-pasts jau pastāv.");
        }
    }

    private void showTeacherMenu(Teacher teacher) {
        boolean running = true;
        while (running) {
            System.out.println("\n========== PASNIEDZĒJA IZVĒLNE ==========");
            System.out.println("Sveiks, " + teacher.getName() + "! (" + teacher.getTeacherId() + ")");
            System.out.println("1. Izveidot kursu");
            System.out.println("2. Skatīt visus kursus");
            System.out.println("3. Izlikt atzīmi studentam");
            System.out.println("4. Skatīt kursa studentus");
            System.out.println("5. Pievienot kursa grafiku");
            System.out.println("6. Skatīt studentu sarakstu");
            System.out.println("7. Pievienot studentu");
            System.out.println("8. Dzēst studentu");
            System.out.println("0. Iziet");
            System.out.print("Izvēle: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createCourse(teacher);
                case "2" -> listAllCourses();
                case "3" -> assignGrade();
                case "4" -> viewCourseStudents();
                case "5" -> addSchedule();
                case "6" -> listAllStudents();
                case "7" -> addStudent();
                case "8" -> removeStudent();
                case "0" -> running = false;
                default -> System.out.println("Nepareiza izvēle. Mēģiniet vēlreiz.");
            }
        }
    }

    private void createCourse(Teacher teacher) {
        System.out.print("Kursa nosaukums: ");
        String name = scanner.nextLine().trim();
        System.out.print("Apraksts: ");
        String description = scanner.nextLine().trim();

        Course course = teacherService.createCourse(teacher.getId(), name, description);
        if (course != null) {
            System.out.println("Kurss izveidots: " + course);
        } else {
            System.out.println("Kļūda veidojot kursu.");
        }
    }

    private void listAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("Nav kursu.");
            return;
        }
        System.out.println("\n--- Visi kursi ---");
        courses.forEach(System.out::println);
    }

    private void assignGrade() {
        listAllStudents();
        System.out.print("Studenta ID: ");
        try {
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Student> optStudent = studentService.findById(studentId);
            if (optStudent.isEmpty()) {
                System.out.println("Students nav atrasts.");
                return;
            }

            listAllCourses();
            System.out.print("Kursa ID: ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Course> optCourse = courseService.findById(courseId);
            if (optCourse.isEmpty()) {
                System.out.println("Kurss nav atrasts.");
                return;
            }

            System.out.print("Atzīme (1-10): ");
            double value = Double.parseDouble(scanner.nextLine().trim());
            if (value < 1 || value > 10) {
                System.out.println("Atzīmei jābūt no 1 līdz 10.");
                return;
            }

            System.out.print("Komentārs (neobligāti): ");
            String comment = scanner.nextLine().trim();

            Grade grade = gradeService.addGrade(optStudent.get(), optCourse.get(), value, comment);
            System.out.println("Atzīme izlikta: " + grade);
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs formāts.");
        }
    }

    private void viewCourseStudents() {
        listAllCourses();
        System.out.print("Kursa ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            List<Student> students = courseService.getEnrolledStudents(courseId);
            if (students.isEmpty()) {
                System.out.println("Kursā nav reģistrētu studentu.");
                return;
            }
            System.out.println("\n--- Kursa studenti ---");
            students.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }

    private void addSchedule() {
        listAllCourses();
        System.out.print("Kursa ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Optional<Course> optCourse = courseService.findById(courseId);
            if (optCourse.isEmpty()) {
                System.out.println("Kurss nav atrasts.");
                return;
            }

            System.out.print("Nedēļas diena (piem. Pirmdiena): ");
            String day = scanner.nextLine().trim();
            System.out.print("Sākuma laiks (piem. 09:00): ");
            String startTime = scanner.nextLine().trim();
            System.out.print("Beigu laiks (piem. 10:30): ");
            String endTime = scanner.nextLine().trim();
            System.out.print("Telpa (piem. 101): ");
            String room = scanner.nextLine().trim();

            Schedule schedule = scheduleService.addSchedule(optCourse.get(), day, startTime, endTime, room);
            System.out.println("Grafiks pievienots: " + schedule);
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }

    private void listAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Nav reģistrētu studentu.");
            return;
        }
        System.out.println("\n--- Visi studenti ---");
        students.forEach(System.out::println);
    }

    private void addStudent() {
        System.out.print("Vārds Uzvārds: ");
        String name = scanner.nextLine().trim();
        System.out.print("E-pasts: ");
        String email = scanner.nextLine().trim();
        System.out.print("Parole: ");
        String password = scanner.nextLine().trim();

        Student student = studentService.addStudent(name, email, password);
        if (student != null) {
            System.out.println("Students pievienots: " + student);
        } else {
            System.out.println("Kļūda: šāds e-pasts jau pastāv.");
        }
    }

    private void removeStudent() {
        listAllStudents();
        System.out.print("Studenta ID dzēšanai: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean success = studentService.removeStudent(id);
            if (success) {
                System.out.println("Students veiksmīgi dzēsts.");
            } else {
                System.out.println("Students ar šādu ID nav atrasts.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Nepareizs ID formāts.");
        }
    }
}
