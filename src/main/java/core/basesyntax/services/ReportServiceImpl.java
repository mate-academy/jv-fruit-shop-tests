package core.basesyntax.services;

import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String NULL_VALUE = "The value cannot be null";
    private static final String COMMA = ",";

    @Override
    public String generateReport(Map<String, Integer> storage) {
        StringBuilder report = new StringBuilder();
        report.append(FIRST_LINE);
        checkOnNull(storage);
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue());
        }
        String resultReport = report.toString();
        report.setLength(0);
        return resultReport;
    }

    private void checkOnNull(Map<String, Integer> storage) {
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new NullPointerException(NULL_VALUE + entry.getKey());
            }
        }
    }
}
