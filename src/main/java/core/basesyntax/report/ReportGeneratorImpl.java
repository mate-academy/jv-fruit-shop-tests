package core.basesyntax.report;

import core.basesyntax.storage.FruitStorage;
import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "Fruit,Quantity" + System.lineSeparator();
    private static final String SEPARATOR = ",";
    private FruitStorage fruitStorage;

    public ReportGeneratorImpl(FruitStorage fruitStorage) {
        this.fruitStorage = fruitStorage;
    }

    @Override
    public String generateReport() {
        Map<String, Integer> fruits = new TreeMap<>(fruitStorage.getFruits());
        if (fruits.isEmpty()) {
            return HEADER;
        }
        StringBuilder builder = new StringBuilder(HEADER);
        for (Map.Entry<String, Integer> entry: fruits.entrySet()) {
            builder.append(entry.getKey())
                    .append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
