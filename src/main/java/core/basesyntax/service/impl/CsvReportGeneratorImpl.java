package core.basesyntax.service.impl;

import core.basesyntax.service.CsvReportGenerator;
import java.util.Map;
import java.util.Optional;

public class CsvReportGeneratorImpl implements CsvReportGenerator<String, Map<String, Integer>> {
    private static final String CSV_SPLITTER = ",";
    private static final String CSV_HEAD_ROW = "fruit,quantity";

    @Override
    public String generateCsvReport(Map<String, Integer> data) {
        Optional.ofNullable(data).orElseThrow(
                () -> new RuntimeException("Data can`t be null"));
        StringBuilder reportStringBuilder = new StringBuilder();
        reportStringBuilder.append(CSV_HEAD_ROW);
        data.forEach((fruit, quantity) -> reportStringBuilder
                .append(System.lineSeparator())
                .append(fruit)
                .append(CSV_SPLITTER)
                .append(quantity));
        return reportStringBuilder.toString();
    }
}
