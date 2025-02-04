package service;

import dao.TransactionsDao;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import model.FruitTransaction;

public class CsvWriteService implements Exporter {
    private final Map<String, Integer> allTransactions;
    private static final String SEPARATOR = ",";
    private static final String REPORT_HEADER = "fruit,quantity\n";
    private static final String LINE_BRAKE = "\n";

    public CsvWriteService(Map<String, Integer> allTransactions) {
        this.allTransactions = allTransactions;
    }

    @Override
    public void exportToCsv(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(REPORT_HEADER);
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
