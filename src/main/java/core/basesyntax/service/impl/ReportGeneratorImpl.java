package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.strorage.FruitStorage;

public class ReportGeneratorImpl implements ReportGenerator {
    public static final String HEADER = "fruit,quantity";

    @Override
    public String generate() {
        StringBuilder resultDataString = new StringBuilder(HEADER);
        FruitStorage.fruits.forEach((k, v) -> resultDataString.append(System.lineSeparator())
                .append(k)
                .append(",")
                .append(v));
        return resultDataString.toString();
    }
}
