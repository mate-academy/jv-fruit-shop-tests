package core.basesyntax.report;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String COMMA = ",";
    private static final String HEAD = "fruit,quantity";

    @Override
    public String getReport(Map<String, Integer> fruitStorage) {
        StringBuilder report = new StringBuilder(HEAD + System.lineSeparator());
        if (fruitStorage == null) {
            return report.toString();
        }
        for (Map.Entry<String, Integer> entry : fruitStorage.entrySet()) {
            report.append(entry.getKey()).append(COMMA)
                    .append(entry.getValue()).append(System.lineSeparator());
        }
        return report.toString();
    }
}
