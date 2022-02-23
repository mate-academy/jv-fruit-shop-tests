package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ManipulationServiceException;
import core.basesyntax.service.ReportService;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_CAPTION = "fruit,quantity";
    private static final String REPORT_SEPARATOR = ",";

    @Override
    public String getReport() {
        if (!Storage.fruits.isEmpty()) {
            StringBuilder stringForReport = new StringBuilder(REPORT_CAPTION);
            Storage.fruits.forEach((key, value) -> stringForReport
                    .append(System.lineSeparator())
                    .append(key.getName())
                    .append(REPORT_SEPARATOR)
                    .append(value));
            return stringForReport.toString();
        } else {
            throw new ManipulationServiceException("Can't create report");
        }
    }
}
