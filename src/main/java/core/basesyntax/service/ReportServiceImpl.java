package core.basesyntax.service;

import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_HEADER = "fruit,quantity";

    @Override
    public String createReport(Map<String, Integer> endOfDayQuantities) {
        isNullMap(endOfDayQuantities);
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER);
        for (String productName : endOfDayQuantities.keySet()) {
            report.append(System.lineSeparator() + productName
                    + "," + endOfDayQuantities.get(productName));
        }
        return report.toString();
    }

    private void isNullMap(Map<String, Integer> endOfDayQuantities) {
        if (endOfDayQuantities == null) {
            throw new RuntimeException("Map can't be null");
        }
    }
}
