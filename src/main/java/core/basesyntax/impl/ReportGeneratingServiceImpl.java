package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratingService;
import java.util.Set;

public class ReportGeneratingServiceImpl implements ReportGeneratingService {
    private static final String DELIMITER = ",";
    private static final String HEADER = "fruit,quantity";

    @Override
    public String generateReportViaStorage() {
        Set<String> fruits = Storage.fruitDB.keySet();
        StringBuilder stringBuilder = new StringBuilder(HEADER);

        for (String fruit : fruits) {
            handleErrors(fruit);
            int quantity = Storage.fruitDB.get(fruit);
            stringBuilder.append(System.lineSeparator())
                    .append(fruit)
                    .append(DELIMITER)
                    .append(quantity);
        }
        return stringBuilder.toString();
    }

    private void handleErrors(String fruit) {
        if (Storage.fruitDB.get(fruit) == null) {
            throw new RuntimeException("The value is NULL");
        }

    }
}
