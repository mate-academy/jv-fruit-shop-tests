package core.basesyntax.service.implementations;

import java.util.Map;

public class ReportCreator {
    private static final String REPORT_TEMPLATE = "fruit,quantity";

    public String provideReport(Map<String, Integer> storageData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : storageData.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(",")
                    .append(entry.getValue());
        }
        return REPORT_TEMPLATE + stringBuilder;
    }
}
