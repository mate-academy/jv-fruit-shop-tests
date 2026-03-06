package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String NEW_LINE = "\r\n";

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("fruit,quantity").append(NEW_LINE);

        for (Map.Entry<String, Integer> entry
                : Storage.getFruitStorage().entrySet()) {

            sb.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(NEW_LINE);
        }

        return sb.toString();
    }
}
