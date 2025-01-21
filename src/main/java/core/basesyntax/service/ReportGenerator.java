package core.basesyntax.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportGenerator {
    public String generateReport(Map<String, Integer> inventory) {
        Map<String, Integer> sortedInventory = new LinkedHashMap<>(inventory);
        StringBuilder report = new StringBuilder("fruit,quantity");
        for (Map.Entry<String, Integer> entry : sortedInventory.entrySet()) {
            report.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(",")
                    .append(entry.getValue());
        }
        return report.toString();
    }
}
