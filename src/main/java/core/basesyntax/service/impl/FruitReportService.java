package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.Set;

public class FruitReportService implements ReportService {
    private static final String COLUMNS = "fruit,quantity";
    private static final String COMMA_SEPARATOR = ",";

    @Override
    public String makeReport() {
        Set<Map.Entry<Fruit, Integer>> entries = Storage.storage.entrySet();
        StringBuilder reportBuilder = new StringBuilder(COLUMNS);
        for (Map.Entry<Fruit, Integer> entry : entries) {
            reportBuilder.append(System.lineSeparator())
                    .append(entry.getKey().getName())
                    .append(COMMA_SEPARATOR)
                    .append(entry.getValue());
        }
        return reportBuilder.toString();
    }
}
