package core.basesyntax.service.impl;

import core.basesyntax.service.Producer;
import java.util.Map;

public class ProduceMapReportService implements Producer {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String COMA = ",";

    @Override
    public String produceReport(Map<String, Integer> map) {
        if (map == null) {
            throw new IllegalArgumentException("can't produce this map because map is null");
        }
        StringBuilder report = new StringBuilder();
        int size = map.size();
        int index = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IllegalArgumentException("can't produce this map integer value below 0");
            }
            report.append(entry.getKey())
                    .append(COMA)
                    .append(entry.getValue());
            if (index < size - 1) {
                report.append(NEW_LINE);
            }
            index++;
        }
        return report.toString();
    }
}
