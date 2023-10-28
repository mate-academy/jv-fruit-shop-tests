package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.Map;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private static final String SEPARATOR = ",";

    @Override
    public String generate() {
        StringBuilder reportBuilder = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator());
        if (Storage.fruits.isEmpty()) {
            return reportBuilder.toString();
        }
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return reportBuilder.toString();
    }
}
