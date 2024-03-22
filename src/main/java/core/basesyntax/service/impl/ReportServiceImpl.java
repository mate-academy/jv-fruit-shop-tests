package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMA = ",";

    @Override
    public String createReport() {
        StringBuilder reportBuilder = new StringBuilder("fruit,quantity" + LINE_SEPARATOR);
        for (Map.Entry<String, Integer> entry : Storage.fruitsStorage.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(COMA)
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR);
        }
        return reportBuilder.toString();
    }
}
