package core.basesyntax.services.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.ShopReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopReportServiceImpl implements ShopReportService {
    private static final String VALUE_SEPARATOR = ",";
    private static final String REPORT_TITLE_ROW = "fruit,quantity";

    @Override
    public List<String> generateReport(Map<String, Integer> processedData) {
        List<String> report = new ArrayList<>();
        report.add(REPORT_TITLE_ROW);
        if (processedData == null) {
            throw new NullDataException("Can't generate report from null input data.");
        }
        processedData.forEach((key, value) -> {
            report.add(key + VALUE_SEPARATOR + value);
        });
        return report;
    }
}
