package com.example.sms.storage;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.example.sms.model.Student;


public class FileStorage {
    private final Path filePath;

    public FileStorage(String fileLocation) {
        this.filePath = Paths.get(fileLocation);
        try { Files.createDirectories(filePath.getParent()); } catch (IOException ignored) {}
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.err.println("Unable to create data file: " + e.getMessage());
            }
        }
    }

    public List<Student> readAll() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCsv(line);
                if (s != null) list.add(s);
            }
        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
        }
        return list;
    }

    public boolean writeAll(List<Student> students) {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Student s : students) {
                bw.write(s.toCsv());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing data file: " + e.getMessage());
            return false;
        }
    }
}
