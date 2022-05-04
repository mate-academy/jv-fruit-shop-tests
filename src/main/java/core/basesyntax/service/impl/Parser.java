package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Parser implements Function<List<String>, List<FruitTransaction>> {
    private static final String COMA = ",";
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_QUANTITY = 2;

    @Override
    public List<FruitTransaction> apply(List<String> stringList) {
        if (stringList == null) {
            throw new RuntimeException("Input list is null");
        }
        return stringList.stream()
                .filter(e -> !e.startsWith("type"))
                .map(e -> {
                    String[] values = e.split(COMA);
                    return new FruitTransaction(new Fruit(values[INDEX_OF_FRUIT]),
                            Integer.parseInt(values[INDEX_OF_QUANTITY]),
                            values[INDEX_OF_OPERATION]);
                })
                .collect(Collectors.toList());
    }
}
