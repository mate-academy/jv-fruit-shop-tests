package core.basesyntax.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REPORT_HEADER = "fruit,quantity\n";
    private static final String SEPARATOR = ",";

    @Override
    public String getReport(Map<String, Integer> storage) {
        StringBuilder report = new StringBuilder("fruit,quantity");

        List<String> sortedKeys = new ArrayList<>(storage.keySet());
        Collections.sort(sortedKeys);

        for (String fruit : sortedKeys) {
            report.append(System.lineSeparator())
                    .append(fruit.trim())
                    .append(",")
                    .append(storage.get(fruit));
        }

        report.append(System.lineSeparator());
        return report.toString();
    }
}
