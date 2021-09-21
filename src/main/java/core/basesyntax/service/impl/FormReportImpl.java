package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FormReport;

public class FormReportImpl implements FormReport {
    private static final String REPORT_HEAD = "fruit,quantity";

    @Override
    public String reportFromStorage() {
        StringBuilder stringBuilder = new StringBuilder(REPORT_HEAD);
        Storage.storage.forEach((key, value) -> stringBuilder.append(System.lineSeparator())
                .append(key.getName())
                .append(",")
                .append(value));
        return stringBuilder.toString();
    }
}
