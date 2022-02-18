package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.service.ReportCreatorService;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";

    @Override
    public String createReport() {
        if (!Storage.fruitBalance.isEmpty()) {
            StringBuilder reportBuilder = new StringBuilder(HEADER);
            Storage.fruitBalance.forEach((key, value) -> reportBuilder
                    .append(System.lineSeparator())
                    .append(key.getFruitName())
                    .append(SEPARATOR)
                    .append(value));
            return reportBuilder.toString();
        } else {
            throw new DataProcessingException("Can't create report from empty report");
        }
    }
}
