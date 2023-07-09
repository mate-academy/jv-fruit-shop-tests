package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private static final String TITLE = "fruit,quantity";
    private static final String DELIMITER = ",";

    @Override
    public String generateReport() {

        StringBuilder record = new StringBuilder();
        record.append(TITLE).append(System.lineSeparator());

        Storage.fruits.forEach((fruit, quantity) -> record.append(fruit)
                .append(DELIMITER)
                .append(quantity)
                .append(System.lineSeparator()));
        return record.toString();
    }
}
