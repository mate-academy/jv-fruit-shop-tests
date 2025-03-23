package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorService {

    public String generateReport(Map<String, Integer> inventory) {
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("fruit,quantity").append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return reportBuilder.toString();
    }
}
