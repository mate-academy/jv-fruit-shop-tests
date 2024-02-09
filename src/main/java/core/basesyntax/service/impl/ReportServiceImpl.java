package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private final String separator = "\n";

    @Override
    public String createReport() {
        StringBuilder line = new StringBuilder();
        line.append("fruit,quantity" + separator);
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            line.append(entry.getKey() + "," + entry.getValue() + separator);
        }
        return line.toString();
    }
}
