package core.basesyntax.report;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport(Map<String, Integer> inventory) {
        StringBuilder report = new StringBuilder(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            report.append(entry.getKey()).append(",")
                    .append(entry.getValue()).append("\n");
        }
        return report.toString();
    }
}
