package core.basesyntax.service;

import core.basesyntax.ReportGenerator;
import core.basesyntax.Storage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REPORT_HEADER = "fruit, quantity";

    @Override
    public String getReport(Storage storage) {
        StringBuilder builder = new StringBuilder(REPORT_HEADER);
        builder.append(":")
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : storage.getAllFruits().entrySet()) {
            builder.append(entry.getKey())
                    .append(", ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
