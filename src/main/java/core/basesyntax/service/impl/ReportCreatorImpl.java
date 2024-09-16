package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitsMap;

import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String COMMA_SEPARATOR = ",";
    private static final String REPORT_SEPARATOR = "\n";

    @Override
    public String doReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(REPORT_HEADER);
        for (Map.Entry<String, Integer> entry : fruitsMap.entrySet()) {
            stringBuilder.append(REPORT_SEPARATOR).append(entry.getKey())
                    .append(COMMA_SEPARATOR).append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
