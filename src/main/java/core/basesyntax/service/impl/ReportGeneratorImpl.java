package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA_REGEX_SEPARATOR = ",";

    @Override
    public String getReport() {
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER);
        for (Map.Entry<String, Integer> entry : Storage.getFruitStock().entrySet()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(COMMA_REGEX_SEPARATOR)
                    .append(entry.getValue());
        }
        return builder.toString();

    }
}
