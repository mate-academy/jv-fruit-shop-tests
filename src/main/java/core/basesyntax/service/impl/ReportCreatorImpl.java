package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;

public class ReportCreatorImpl implements ReportCreator {
    private StringBuilder reportMessage = new StringBuilder();

    @Override
    public String createReport() {
        for (var value : Storage.fruits.entrySet()) {
            reportMessage.append(value.getKey())
                    .append(",")
                    .append(value.getValue())
                    .append(System.lineSeparator());
        }
        //reportMessage.replace(reportMessage.length() - 2, reportMessage.length(), "");

        return reportMessage.toString();
    }
}
