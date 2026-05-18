package university.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogService {

    private static LogService instance;
    private final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LogService() {}

    public static LogService getInstance() {
        if (instance == null) { instance = new LogService(); }
        return instance;
    }

    public void log(String userId, String action) {
        String entry = "[" + LocalDateTime.now().format(FMT) + "] User#" + userId + " -> " + action;
        logs.add(entry);
    }

    public void logSystem(String systemSource, String action) {
        String entry = "[" + LocalDateTime.now().format(FMT) + "] [" + systemSource + "] " + action;
        logs.add(entry);
    }

    public void logE(String systemSource, String action) {
        logSystem(systemSource, action); 
    }

    public void printLogs() {
        if (logs.isEmpty()) {
            System.out.println("  (no log entries yet)");
            return;
        }
        logs.forEach(System.out::println);
    }

    public void printRecentLogs(int n) {
        int start = Math.max(0, logs.size() - n);
        logs.subList(start, logs.size()).forEach(System.out::println);
    }

    public List<String> getLogs() { return new ArrayList<>(logs); }
    public void clearLogs() { logs.clear(); }
}