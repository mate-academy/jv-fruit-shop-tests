package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruits;

import core.basesyntax.service.CreateReportService;
import java.util.Map;

public class CreateReportImpl implements CreateReportService {
    private static final String HEADER_FRUIT = "fruitTransaction,";
    private static final String HEADER_QUANTITY = "quantity";

    @Override
    public String generateReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER_FRUIT)
                .append(HEADER_QUANTITY);
        for (Map.Entry<String, Integer> map : fruits.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(map.getKey())
                    .append(" ")
                    .append(map.getValue());
        }
        return stringBuilder.toString();
    }
}
