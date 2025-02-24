package core.basesyntax.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String createReport() {
        StringBuilder report = new StringBuilder().append(FIRST_LINE)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            report.append(entry.getKey()).append(COMMA)
                    .append(entry.getValue()).append(System.lineSeparator());
        }
        return report.toString();
    }
}
