package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;

public class ReportCreatorImpl implements ReportCreator {
    private static final String REPORT_STARTING = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String createReport() {
        StringBuilder builder = new StringBuilder().append(REPORT_STARTING);
        Storage.storage.forEach((key, value) -> builder.append(System.lineSeparator())
                .append(key.getName())
                .append(COMMA)
                .append(value));
        return builder.toString();
    }
}
