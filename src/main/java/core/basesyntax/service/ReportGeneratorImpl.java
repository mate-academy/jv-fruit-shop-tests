package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REPORT_HEADER = "fruit,quantity" + System.lineSeparator();
    private final Map<String, Integer> storage;

    public ReportGeneratorImpl(Map<String, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder(REPORT_HEADER);
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.append(entry.getKey())
                    .append(", ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
