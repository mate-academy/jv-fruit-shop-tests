package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String generateReport(Map<String, Integer> fruits) {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity");
        for (Map.Entry<String, Integer> entry : fruits.entrySet()) {
            report.append(String.format("%s%s,%d",System.lineSeparator(), entry.getKey(),
                    entry.getValue()));
        }
        return report.toString();
    }
}
