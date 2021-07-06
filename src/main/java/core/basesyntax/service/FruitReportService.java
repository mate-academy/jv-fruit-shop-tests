package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitReportService implements ReportService {
    public static final String FILE_CSV_TITLE = "fruit,quantity" + System.lineSeparator();
    private static final char CSV_SEPARATOR = ',';
    private Map<Fruit, Integer> storage;

    public FruitReportService(Map<Fruit, Integer> storage) {
        this.storage = storage;
    }

    public String getReport() {
        StringBuilder stringBuilder = new StringBuilder(FILE_CSV_TITLE);
        storage.forEach((key, value) -> stringBuilder
                .append(key.getName())
                .append(CSV_SEPARATOR)
                .append(value)
                .append(System.lineSeparator()));
        return stringBuilder.toString().trim();
    }
}
