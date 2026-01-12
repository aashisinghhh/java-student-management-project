package com.example.sms.util;
import com.example.sms.model.Student;

import java.util.*;
import java.util.stream.Collectors;
public class ReportGenerator {
    public static Map<String, Long> countByCourse(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
    }

    public static double averageAge(List<Student> students) {
        return students.stream().mapToInt(Student::getAge).average().orElse(0.0);
    }

    public static String simpleReport(List<Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total students: ").append(students.size()).append("\n");
        sb.append(String.format("Average age: %.2f\n", averageAge(students)));
        sb.append("Count by course:\n");
        Map<String, Long> map = countByCourse(students);
        for (Map.Entry<String, Long> e : map.entrySet()) {
            sb.append("  ").append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
