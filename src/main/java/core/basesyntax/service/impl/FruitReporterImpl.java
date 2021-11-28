package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitReporter;
import java.util.Map;

public class FruitReporterImpl implements FruitReporter {
    private StringBuilder stringBuilder;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public FruitReporterImpl() {
        stringBuilder = new StringBuilder();
    }

    @Override
    public String report(Map<Fruit, Integer> fruitsStorage) {
        stringBuilder.append("fruit,quantity" + LINE_SEPARATOR);
        for (Map.Entry<Fruit, Integer> fruitEntry : fruitsStorage.entrySet()) {
            stringBuilder.append(fruitEntry.getKey().getFruitName() + ","
                    + fruitEntry.getValue() + LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }
}
