package core.basesyntax.dao.impl;

import core.basesyntax.dao.ReportGenerator;
import core.basesyntax.db.FruitStorage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final FruitStorage fruitStorage;

    public ReportGeneratorImpl(FruitStorage fruitStorage) {
        this.fruitStorage = fruitStorage;
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder(HEADER + LINE_SEPARATOR);

        fruitStorage.getFruits().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(entry.getKey())
                        .append(SEPARATOR)
                        .append(entry.getValue())
                        .append(LINE_SEPARATOR));
        return report.toString();
    }
}
