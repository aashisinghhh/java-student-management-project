package com.example.sms.ui;
import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import com.example.sms.util.ReportGenerator;
import com.example.sms.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {
    private final StudentService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(StudentService service) { this.service = service; }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": createStudent(); break;
                case "2": listStudents(); break;
                case "3": updateStudent(); break;
                case "4": deleteStudent(); break;
                case "5": printReport(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n=== Student Management ===");
        System.out.println("1) Add student");
        System.out.println("2) List students");
        System.out.println("3) Update student");
        System.out.println("4) Delete student");
        System.out.println("5) Show report");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private void createStudent() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = parseIntInput();
        System.out.print("Course: ");
        String course = scanner.nextLine().trim();

        if (!Validator.isNonEmpty(name) || !Validator.isNonEmpty(course) || !Validator.isPositiveInt(age)) {
            System.out.println("Invalid input. Aborting create.");
            return;
        }
        Student s = service.create(name, age, course);
        System.out.println("Created: " + s);
    }

    private void listStudents() {
        List<Student> all = service.listAll();
        if (all.isEmpty()) {
            System.out.println("No students.");
            return;
        }
        all.forEach(System.out::println);
    }

    private void updateStudent() {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine().trim();
        Optional<Student> op = service.getById(id);
        if (op.isEmpty()) { System.out.println("Student not found."); return; }
        System.out.print("New name (leave blank to keep): ");
        String name = scanner.nextLine().trim();
        System.out.print("New age (0 to keep): ");
        int age = parseIntInput();
        System.out.print("New course (leave blank to keep): ");
        String course = scanner.nextLine().trim();

        Student existing = op.get();
        if (name.isEmpty()) name = existing.getName();
        if (age <= 0) age = existing.getAge();
        if (course.isEmpty()) course = existing.getCourse();

        boolean ok = service.update(id, name, age, course);
        System.out.println(ok ? "Updated." : "Update failed.");
    }

    private void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine().trim();
        boolean ok = service.remove(id);
        System.out.println(ok ? "Deleted." : "Not found or delete failed.");
    }

    private void printReport() {
        List<Student> all = service.listAll();
        System.out.println(ReportGenerator.simpleReport(all));
    }

    private int parseIntInput() {
        String line = scanner.nextLine().trim();
        try { return Integer.parseInt(line); } catch (NumberFormatException e) { return 0; }
    }
}
