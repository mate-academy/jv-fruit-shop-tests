package core.basesyntax.db;

import core.basesyntax.db.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String FRUIT_QUANTITY = "fruit, quantity";
    private static final String COMMA = ",";

    @Override
    public String generate() {
        Map<String, Integer> report = Storage.storage;
        StringBuilder builder = new StringBuilder();
        builder.append(FRUIT_QUANTITY)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            builder.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
