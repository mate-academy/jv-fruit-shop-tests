package service.impl;

import db.Storage;
import java.util.Map;
import java.util.stream.Collectors;
import service.ReportGenerator;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,amount";
    private static final String COMMA_DELIMITER = ",";

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        Map<String, Integer> fruits = Storage.fruits.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        report.append(HEADER).append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : fruits.entrySet()) {
            report.append(entry.getKey())
                    .append(COMMA_DELIMITER)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return report.toString();
    }
}
