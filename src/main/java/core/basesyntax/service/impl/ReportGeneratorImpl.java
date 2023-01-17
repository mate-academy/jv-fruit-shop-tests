package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    public static final String FIELD_NAMES = "fruit,quantity";

    @Override
    public String generate() {
        StringBuilder stringBuilder = new StringBuilder(FIELD_NAMES);
        for (Map.Entry<String, Integer> fruitBalance : FruitStorage.fruits.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(fruitBalance.getKey())
                    .append(",")
                    .append(fruitBalance.getValue().toString());
        }
        return stringBuilder.toString();
    }
}
