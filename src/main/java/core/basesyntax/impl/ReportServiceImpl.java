package core.basesyntax.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String COMMA = ",";
    private static final String ENTER = System.lineSeparator();
    private static final String COLUMN_NAME = "fruit,quantity";

    @Override
    public String writeReport(Map<String, Integer> fruits) {
        StringBuilder builder = new StringBuilder();
        builder.append(COLUMN_NAME).append(ENTER);
        fruits.forEach((key, value) -> builder.append(key)
                .append(COMMA).append(value).append(ENTER));
        return builder.toString();
    }
}
