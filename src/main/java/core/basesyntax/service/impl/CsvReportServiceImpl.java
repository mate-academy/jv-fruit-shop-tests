package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;

public class CsvReportServiceImpl implements ReportService {
    private static final String REPORT_LEGEND = "fruit,quantity";
    private final String separator = ",";
    private final StringBuilder reportBuilder = new StringBuilder();

    @Override
    public String getReport(Map<String, Integer> shopItemQuantityMap) {
        reportBuilder.delete(0, reportBuilder.length());
        reportBuilder.append(REPORT_LEGEND);
        shopItemQuantityMap.forEach((key, value) -> reportBuilder
                .append(System.getProperty("line.separator"))
                .append(key)
                .append(separator)
                .append(value));
        return reportBuilder.toString();
    }
}
