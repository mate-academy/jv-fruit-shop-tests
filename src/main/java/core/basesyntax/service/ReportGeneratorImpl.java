package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String generateReport(Map<String, Integer> storage) {
        if (storage == null) {
            throw new RuntimeException("Storage is null");
        }

        if (storage.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }

        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity\n");

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }
}
