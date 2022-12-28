package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    @Override
    public String createReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit").append(",").append("quantity");
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new NullPointerException("Value can't equals null");
            }
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey()).append(",").append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
