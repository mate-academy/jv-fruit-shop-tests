package core.basesyntax.services.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.ShopReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopReportServiceImpl implements ShopReportService {
    private static final String DEFAULT_TITLE_ROW = "fruit,quantity";
    private static final String DEFAULT_VALUE_SEPARATOR = ",";
    private String valueSeparator;
    private String titleRow;

    public ShopReportServiceImpl(String valueSeparator, String titleRow) {
        this.valueSeparator = valueSeparator == null ? DEFAULT_VALUE_SEPARATOR : valueSeparator;
        this.titleRow = titleRow == null ? DEFAULT_TITLE_ROW : titleRow;
    }

    @Override
    public List<String> generateReport(Map<String, Integer> processedData) {
        List<String> report = new ArrayList<>();
        report.add(titleRow);
        if (processedData == null) {
            throw new NullDataException("Can't generate report from null input data.");
        }
        processedData.forEach((key, value) -> {
            report.add(key + valueSeparator + value);
        });
        return report;
    }
}
