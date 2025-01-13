package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImpl implements ReportGeneratorService {
    private static final String COMMA = ",";
    private static final String HEADER = "fruit,quantity";

    @Override
    public String getReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(HEADER).append(System.lineSeparator());

        Map<String, Integer> sortedFruits = new TreeMap<>(Storage.getAllFruits());

        for (Map.Entry<String, Integer> entry : sortedFruits.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return reportBuilder.toString().trim();
    }

}
