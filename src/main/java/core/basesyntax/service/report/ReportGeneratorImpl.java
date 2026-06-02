package core.basesyntax.service.report;

import core.basesyntax.db.Storage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String FILE_START = "fruit,quantity";

    @Override
    public String getReport() {
        StringBuilder builder = new StringBuilder();
        builder.append(FILE_START).append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : Storage.getStorage().entrySet()) {
            builder.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
