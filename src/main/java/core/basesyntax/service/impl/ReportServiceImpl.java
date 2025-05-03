package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionException;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String CSV_DELIMITER = ",";
    private static final String EXCEPTION_MESSAGE = "Can't create report. Storage is empty: ";

    @Override
    public String createReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(HEADER).append(System.lineSeparator());
        if (Storage.fruits.isEmpty()) {
            throw new TransactionException(EXCEPTION_MESSAGE + Storage.fruits);
        }
        for (Map.Entry<String, Integer> fruit : Storage.fruits.entrySet()) {
            reportBuilder.append(fruit.getKey()).append(CSV_DELIMITER)
                    .append(fruit.getValue()).append(System.lineSeparator());
        }
        return reportBuilder.toString().trim();
    }
}
