package core.basesyntax.services.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportCreatorService;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String DEFAULT_MESSAGE = "fruit,quantity";
    private static final String SPLIT_DELIMITER = ",";

    @Override
    public String createReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(DEFAULT_MESSAGE).append(System.lineSeparator());
        Storage.storage.forEach((key, value) -> reportBuilder.append(key)
                .append(SPLIT_DELIMITER)
                .append(value)
                .append(System.lineSeparator()));
        return reportBuilder.toString();
    }
}
