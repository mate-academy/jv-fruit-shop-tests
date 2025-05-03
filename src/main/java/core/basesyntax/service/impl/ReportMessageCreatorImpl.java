package core.basesyntax.service.impl;

import core.basesyntax.service.ReportMessageCreator;
import java.util.Map;

public class ReportMessageCreatorImpl implements ReportMessageCreator {
    private static final char SEPARATOR_TO_WORDS = ',';
    private static final String FRUIT = "fruit";
    private static final String QUANTITY = "quantity";

    @Override
    public String createMessage(Map<String, Integer> toWrite) {
        if (toWrite == null || toWrite.isEmpty()) {
            throw new RuntimeException("The input parameter "
                    + "couldn't be null or empty!");
        }
        StringBuilder toReport = new StringBuilder();
        toReport.append(FRUIT).append(SEPARATOR_TO_WORDS)
                .append(QUANTITY).append(System.lineSeparator());
        for (Map.Entry<String, Integer> report : toWrite.entrySet()) {
            toReport.append(report.getKey()).append(SEPARATOR_TO_WORDS)
                    .append(report.getValue()).append(System.lineSeparator());
        }
        return toReport.toString();
    }
}
