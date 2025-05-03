package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    public static final String HEAD = "fruit,quantity";
    public static final String SEPARATOR = System.lineSeparator();

    @Override
    public String generateReport() {
        StringBuilder reportBuilder = new StringBuilder(HEAD).append(SEPARATOR);
        for (Map.Entry key : Storage.fruits.entrySet()) {
            reportBuilder.append(key.getKey().toString())
                    .append(",")
                    .append(key.getValue().toString())
                    .append(SEPARATOR);
        }
        return reportBuilder.toString();
    }
}
