package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_LEGEND = "fruit,quantity\n";
    private static final String SEPARATOR = ",";
    private static final Map<String, Integer> STORAGE = Storage.FRUITS;

    @Override
    public String createReport() {
        if (Storage.FRUITS.size() == 0) {
            throw new FruitShopException("Cen`t create report");
        }
        StringBuilder reportBuilder = new StringBuilder(REPORT_LEGEND);
        for (var entry : STORAGE.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return reportBuilder.toString();
    }
}
