package com.example.sms.service;
import com.example.sms.dao.StudentDAO;
import com.example.sms.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentService {
    private final StudentDAO dao;

    public StudentService(StudentDAO dao) { this.dao = dao; }

    public List<Student> listAll() { return dao.getAll(); }

    public Optional<Student> getById(String id) { return dao.findById(id); }

    public Student create(String name, int age, String course) {
        String id = generateId();
        Student s = new Student(id, name, age, course);
        dao.add(s);
        return s;
    }

    public boolean update(String id, String name, int age, String course) {
        Optional<Student> op = dao.findById(id);
        if (op.isPresent()) {
            Student s = op.get();
            s.setName(name);
            s.setAge(age);
            s.setCourse(course);
            return dao.update(s);
        }
        return false;
    }

    public boolean remove(String id) { return dao.delete(id); }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
