package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String TITLE = "fruit, quantity" + System.lineSeparator();

    @Override
    public String buildReport() {
        StringBuilder report = new StringBuilder(TITLE);
        Map<String, Integer> sortedQuantities = new TreeMap<>(Storage.quantities);
        for (Map.Entry<String, Integer> entry : sortedQuantities.entrySet()) {
            report.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
