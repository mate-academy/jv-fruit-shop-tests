package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.Set;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static final String CSV_SEPARATOR = ",";

    @Override
    public String createReport(Set<Map.Entry<Fruit, Integer>> entries) {
        StringBuilder builder = new StringBuilder(HEADER);
        entries.forEach(entry -> {
            builder.append(entry.getKey().getName())
                    .append(CSV_SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        });
        return builder.toString();
    }
}
