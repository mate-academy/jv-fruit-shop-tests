package core.basesyntax.service.parsers.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.parsers.FruitParser;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FruitParserImpl implements FruitParser {
    private Map<String, Fruit> fruitMap;

    public FruitParserImpl() {
        fruitMap = new HashMap<>();
        Arrays.stream(Fruit.values())
                .forEach(f -> fruitMap
                        .put(f.getFruitName(), f));
    }

    @Override
    public Fruit parse(String fruitName) {
        if (fruitName == null || !fruitMap.containsKey(fruitName)) {
            throw new RuntimeException("Fruit name is wrong or null");
        }
        return fruitMap.get(fruitName);
    }
}
