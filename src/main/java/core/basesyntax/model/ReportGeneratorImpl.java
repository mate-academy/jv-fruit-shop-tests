package core.basesyntax.model;

import java.util.Map;
import java.util.StringJoiner;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEADER = "fruit,quantity";

    @Override
    public String getReport(Map<String, Integer> storage) {
        StringJoiner report = new StringJoiner(LINE_SEPARATOR);
        report.add(REPORT_HEADER);

        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.add(entry.getKey() + "," + entry.getValue());
        }

        return report.toString();
    }
}
