package com.example.sms;
import com.example.sms.dao.StudentDAO;
import com.example.sms.service.StudentService;
import com.example.sms.storage.FileStorage;
import com.example.sms.ui.ConsoleUI;

public class App {
    public static void main(String[] args) {
        String dataFile = "data/students.csv";
        FileStorage storage = new FileStorage(dataFile);
        StudentDAO dao = new StudentDAO(storage);
        StudentService service = new StudentService(dao);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}
