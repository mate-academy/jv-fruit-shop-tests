package core.basesyntax.service.impl;

import core.basesyntax.model.StorageDao;
import core.basesyntax.service.ReportProvider;
import java.util.function.Consumer;

public class ReportProviderImpl implements ReportProvider {
    public static final String REPORT_TITLE = "fruit,quantity";
    public static final String COLUMN_SEPARATOR = ",";
    public static final String ROW_SEPARATOR = System.lineSeparator();

    private final Consumer<Integer> positiveValueCheck = operationValue -> {
        if (operationValue < 0) {
            throw new RuntimeException("Operation value cannot be negative");
        }
    };

    @Override
    public String provide() {
        StringBuilder builder = new StringBuilder(REPORT_TITLE + ROW_SEPARATOR);
        StorageDao.storage.entrySet().stream()
                .peek(t -> positiveValueCheck.accept(t.getValue()))
                .map(t -> t.getKey() + COLUMN_SEPARATOR + t.getValue() + ROW_SEPARATOR)
                .forEach(builder::append);
        return builder.toString().trim();
    }
}
