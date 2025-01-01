package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class FruitStrategyImpl implements FruitStrategy {
    private final Fruit fruit;
    private Map<String, BiFunction<String, Integer, Integer>> operationMap;

    public FruitStrategyImpl(Fruit fruit) {
        this.fruit = fruit;
        operationMap = new HashMap<>();

        operationMap.put("b", (fruitType, amount) -> fruit.balance(fruitType, amount));
        operationMap.put("s", (fruitType, amount) -> fruit.supply(fruitType, amount));
        operationMap.put("p", (fruitType, amount) -> fruit.purchase(fruitType, amount));
        operationMap.put("r", (fruitType, amount) -> fruit.returnFruit(fruitType, amount));
    }

    @Override
    public int operation(String operation, String fruitType, int amount) {

        BiFunction<String, Integer, Integer> stringIntegerIntegerBiFunction =
                operationMap.get(operation);
        if (stringIntegerIntegerBiFunction == null) {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        return stringIntegerIntegerBiFunction.apply(fruitType, amount);
    }
}