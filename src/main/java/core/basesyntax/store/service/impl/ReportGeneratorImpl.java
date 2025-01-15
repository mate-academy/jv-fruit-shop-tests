package core.basesyntax.store.service.impl;

import core.basesyntax.store.db.Storage;
import core.basesyntax.store.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String getReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(HEADER).append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : Storage.getAllFruits().entrySet()) {
            reportBuilder.append(entry.getKey())
                         .append(COMMA)
                         .append(entry.getValue())
                         .append(System.lineSeparator());
        }

        return reportBuilder.toString();
    }
}
