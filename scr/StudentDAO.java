package com.example.sms.dao;
import com.example.sms.model.Student;
import com.example.sms.storage.FileStorage;

import java.util.*;
import java.util.stream.Collectors;
public class StudentDAO {

    private final FileStorage storage;
    private List<Student> cache;

    public StudentDAO(FileStorage storage) {
        this.storage = storage;
        this.cache = storage.readAll();
    }

    public List<Student> getAll() {
        return new ArrayList<>(cache);
    }

    public Optional<Student> findById(String id) {
        return cache.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public boolean add(Student s) {
        if (findById(s.getId()).isPresent()) return false;
        cache.add(s);
        return storage.writeAll(cache);
    }

    public boolean update(Student updated) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId().equals(updated.getId())) {
                cache.set(i, updated);
                return storage.writeAll(cache);
            }
        }
        return false;
    }

    public boolean delete(String id) {
        int start = cache.size();
        cache = cache.stream().filter(s -> !s.getId().equals(id)).collect(Collectors.toList());
        if (cache.size() < start) {
            return storage.writeAll(cache);
        }
        return false;
    }
