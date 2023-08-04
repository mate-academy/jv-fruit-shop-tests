package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ReportGeneratorException;
import core.basesyntax.service.ReportGeneratorService;
import java.util.Map;
import java.util.Set;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMA_SPLITTER = ",";
    private static final String HEAD_OF_THE_OUTPUT_FILE = "fruit,quantity";

    @Override
    public String generateReport() {
        if (Storage.getStorage().isEmpty()) {
            throw new ReportGeneratorException("Can't create report"
                    + ", because there aren't any fruit in the Storage");
        }
        StringBuilder builder = new StringBuilder(HEAD_OF_THE_OUTPUT_FILE);
        Set<Map.Entry<String, Integer>> fruits = Storage.getStorage().entrySet();
        for (Map.Entry<String, Integer> fruit : fruits) {
            builder.append(LINE_SEPARATOR)
                    .append(fruit.getKey())
                    .append(COMA_SPLITTER)
                    .append(fruit.getValue());
        }
        return builder.toString();
    }
}
