package service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CsvWriteService implements Exporter {
    private static final String SEPARATOR = ",";
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String LINE_BRAKE = System.lineSeparator();
    private final Map<String, Integer> allTransactions;

    public CsvWriteService(Map<String, Integer> allTransactions) {
        this.allTransactions = allTransactions;
    }

    @Override
    public void exportToCsv(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(REPORT_HEADER);
            writer.append(LINE_BRAKE);
            for (Map.Entry<String, Integer> fruit : allTransactions.entrySet()) {
                writer.append(fruit.getKey())
                        .append(SEPARATOR)
                        .append(String.valueOf(fruit.getValue()))
                        .append(LINE_BRAKE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to CSV file: " + fileName, e);
        }
    }
}
