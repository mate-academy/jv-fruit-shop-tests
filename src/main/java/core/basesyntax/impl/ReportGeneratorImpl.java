package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    public static final String CSV_FILE_HEADER = "fruit,quantity";
    private static final String COMMA_SEPARATOR = ",";

    @Override
    public String getReport() {
        if (Storage.getFruitStorage().isEmpty()) {
            return CSV_FILE_HEADER;
        }
        return Storage.getFruitStorage().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + COMMA_SEPARATOR + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator(),
                        CSV_FILE_HEADER + System.lineSeparator(), ""));
    }
}
