package core.basesyntax.fruit.shop.service.impl;

import core.basesyntax.fruit.shop.db.Storage;
import core.basesyntax.fruit.shop.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String TITLE = "fruit,quantity\n";
    private static final String DELIMITER = ",";

    @Override
    public String generateReport() {
        Map<String, Integer> records = Storage.FRUITS;
        StringBuilder record = new StringBuilder(TITLE);
        for (Map.Entry<String, Integer> entry : records.entrySet()) {
            record.append(entry.getKey())
                    .append(DELIMITER)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return record.toString();
    }
}
