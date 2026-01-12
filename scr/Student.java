package com.example.sms.model;

public class Student {
    private String id;
    private String name;
    private int age;
    private String course;

    public Student(String id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }

    public String toCsv() {
        return id + "," + name.replace(",", " ") + "," + age + "," + course.replace(",", " ");
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Age: %d | Course: %s", id, name, age, course);
    }

    public static Student fromCsv(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split(",", -1);
        if (parts.length < 4) return null;
        String id = parts[0].trim();
        String name = parts[1].trim();
        int age = 0;
        try { age = Integer.parseInt(parts[2].trim()); } catch (NumberFormatException e) { age = 0; }
        String course = parts[3].trim();
        return new Student(id, name, age, course);
    }
}
