package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.CsvReportService;
import java.util.Map;

public class CsvReportServiceImpl implements CsvReportService {
    @Override
    public String createReport(Map<Fruit, Integer> storage) {
        if (storage.isEmpty()) {
            return "No data provided";
        }
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator());
        for (Map.Entry<Fruit, Integer> record : storage.entrySet()) {
            stringBuilder.append(record.getKey()).append(",")
                    .append(record.getValue()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
