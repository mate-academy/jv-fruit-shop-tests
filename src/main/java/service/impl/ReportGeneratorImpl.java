package service.impl;

import db.Storage;
import java.util.Map;
import service.ReportGenerator;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,amount";
    private static final String COMMA_DELIMITER = ",";

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();

        report.append(HEADER).append(System.lineSeparator());
        Storage.fruits.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(entry.getKey())
                        .append(COMMA_DELIMITER)
                        .append(entry.getValue())
                        .append(System.lineSeparator()));

        return report.toString();
    }
}
