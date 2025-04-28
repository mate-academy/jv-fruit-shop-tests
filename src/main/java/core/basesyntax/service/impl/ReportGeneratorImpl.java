package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.service.ReportGenerator;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String REGEX_TO_SPLIT = ",";
    private final Storage storage = new StorageImpl();
    private final StringBuilder builder = new StringBuilder();

    @Override
    public String getReport() {
        builder.append(HEADER).append(System.lineSeparator());
        storage.getAll().forEach((key, value) -> builder.append(key)
                .append(REGEX_TO_SPLIT)
                .append(value)
                .append(System.lineSeparator()));
        return builder.toString();
    }
}
