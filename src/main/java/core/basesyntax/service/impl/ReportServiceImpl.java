package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String COLUMNS_NAME = "fruit,quantity";
    private static final String REPORT_FORMAT = "%s,%d";

    @Override
    public String createReport() {
        StringBuilder report = new StringBuilder(COLUMNS_NAME);
        report.append(System.lineSeparator())
                .append(Storage.fruitList.stream()
                        .map(f -> String.format(REPORT_FORMAT, f.getName(), f.getAmount()))
                        .collect(Collectors.joining(System.lineSeparator())));
        return report.toString();
    }
}
