package core.basesyntax.service.impl;

import core.basesyntax.exceptions.ReportGeneratorException;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorCsvImpl implements ReportGenerator {
    private static final String TITLE_ROW = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FIELD_SEPARATOR = ",";

    @Override
    public String create(Map<String, Integer> reportData) {
        checkReportDataObj(reportData);

        StringBuilder builder = new StringBuilder();

        builder
                .append(TITLE_ROW)
                .append(LINE_SEPARATOR);

        reportData.forEach((key, value) -> {
            checkReportDataEntry(key, value);
            builder
                    .append(key)
                    .append(FIELD_SEPARATOR)
                    .append(value)
                    .append(LINE_SEPARATOR);
        });

        return builder.toString();
    }

    private void checkReportDataObj(Map<String, Integer> reportData) {
        if (reportData == null) {
            throw new ReportGeneratorException("reportData object can't be null!");
        }

        if (reportData.isEmpty()) {
            throw new ReportGeneratorException("reportData object can't be empty!");
        }
    }

    private void checkReportDataEntry(String entryKey, Integer entryValue) {
        if (entryKey == null) {
            throw new ReportGeneratorException("reportData entryKey can't be null");
        }
        if (entryKey.isEmpty()) {
            throw new ReportGeneratorException("reportData entryKey can't be empty");
        }
        if (entryValue == null) {
            throw new ReportGeneratorException("reportData entryValue can't be null");
        }
        if (entryValue < 0) {
            throw new ReportGeneratorException("reportData entryValue can't be negative");
        }
    }
}
