package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public void generateReport(Map<String, Integer> storage, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("fruit,quantity");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : storage.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find path: " + filePath, e);
        }
    }
}
