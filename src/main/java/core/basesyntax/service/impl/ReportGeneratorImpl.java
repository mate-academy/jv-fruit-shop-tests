package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport() {
        if (!Storage.getFruitStock().isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("fruit,quantity");
            for (Map.Entry<String, Integer> entry : Storage.getFruitStock().entrySet()) {
                builder.append(System.lineSeparator())
                        .append(entry.getKey())
                        .append(",")
                        .append(entry.getValue());
            }
            return builder.toString();
        } else {
            return "No data available for the report";
        }
    }
}
