package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String COLUMNS_TITLE = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public String getReport() {
        if (Storage.FRUITS.isEmpty()) { //added after approval
            throw new RuntimeException("Data storage with Fruits is empty");
        }
        StringBuilder summaryStringReport = new StringBuilder(COLUMNS_TITLE);
        String stringReport = Storage.FRUITS.entrySet().stream()
                .map(entry -> entry.getKey() + SEPARATOR + entry.getValue())
                .sorted()
                .collect(Collectors.joining(LINE_SEPARATOR));
        return summaryStringReport.append(LINE_SEPARATOR).append(stringReport).toString();
    }
}
