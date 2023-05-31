package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String ITEMS_SEPARATOR = ",";

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(HEADER)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> storage : FruitDao.getAll().entrySet()) {
            report.append(storage.getKey())
                    .append(ITEMS_SEPARATOR)
                    .append(storage.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}

