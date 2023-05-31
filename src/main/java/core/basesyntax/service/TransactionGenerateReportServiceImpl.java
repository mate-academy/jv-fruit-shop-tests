package core.basesyntax.service;

import java.util.Map;

public class TransactionGenerateReportServiceImpl implements TransactionGenerateReportService {
    private static final String SIGN_SEPARATOR = ",";
    private static final String FIRST_LINE_REPORT = "fruit,quantity";

    @Override
    public String generateReport(Map<String, Integer> fruitsCount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FIRST_LINE_REPORT)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> line : fruitsCount.entrySet()) {
            stringBuilder.append(line.getKey())
                    .append(SIGN_SEPARATOR)
                    .append(line.getValue())
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
