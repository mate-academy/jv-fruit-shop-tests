package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String generateReport(Map<String, Integer> storage) {
        if (storage.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        if (storage.get("fruit") == null) {
            throw new RuntimeException("Fruit not found" + storage.get("fruit"));
        }

        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity\n");

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }
}
