package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportBuilderService;
import java.util.Map;

public class ReportBuilderServiceImpl implements ReportBuilderService {
    private static final String HEADER = "fruit,quantity";

    @Override
    public String buildReport() {
        if (Storage.fruits.isEmpty()) {
            return "";
        }
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(HEADER);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            if (entry.getValue() == null || entry.getKey() == null) {
                continue;
            }
            reportBuilder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(",")
                    .append(entry.getValue());
        }
        return reportBuilder.toString();
    }
}
