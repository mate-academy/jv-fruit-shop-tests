package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String TITLE = "fruit,quantity";
    private static final String SEPARATOR = ",";

    @Override
    public String getReport(Map<String, Integer> storage) {
        if (storage == null) {
            throw new RuntimeException("Storage map must not be null");
        }
        StringBuilder report = new StringBuilder();
        report.append(TITLE).append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new RuntimeException("Fruit name and quantity can not be null");
            }
            report.append(entry.getKey())
                    .append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
