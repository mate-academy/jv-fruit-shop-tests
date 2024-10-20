package core.basesyntax.report;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REPORT_HEADER = "fruit,quantity" + System.lineSeparator();

    @Override
    public String getReport(Map<String, Integer> storage) {
        StringBuilder report = new StringBuilder(REPORT_HEADER);

        storage.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(entry.getKey())
                        .append(",")
                        .append(entry.getValue())
                        .append(System.lineSeparator()));
        return report.toString();
    }
}
