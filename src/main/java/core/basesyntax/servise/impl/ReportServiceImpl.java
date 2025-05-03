package core.basesyntax.servise.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public String createReport() {
        StringBuilder report = new StringBuilder("fruit,quantity");
        for (Map.Entry<String, Integer> record : Storage.items.entrySet()) {
            report.append(System.lineSeparator())
                    .append(record.getKey())
                    .append(COMMA_DELIMITER)
                    .append(record.getValue());
        }
        return report.toString();
    }
}
