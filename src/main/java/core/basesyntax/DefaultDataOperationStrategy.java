package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class DefaultDataOperationStrategy {

    private final Map<Operation, OperationHandler> handlers = new HashMap<>();

    public DefaultDataOperationStrategy(FruitDB fruitDB) {
        handlers.put(Operation.BALANCE, (fruit, quantity) -> fruitDB.add(fruit, quantity));
        handlers.put(Operation.SUPPLY, (fruit, quantity) -> fruitDB.add(fruit, quantity));
        handlers.put(Operation.PURCHASE, (fruit, quantity) -> {
            if (quantity > fruitDB.getInventory().getOrDefault(fruit, 0)) {
                throw new IllegalArgumentException("Not enough inventory for purchase");
            }
            fruitDB.subtract(fruit, quantity);
        });
        handlers.put(Operation.RETURN, (fruit, quantity) -> fruitDB.add(fruit, quantity));
    }

    public void execute(String type, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Transaction quantity cannot be negative");
        }
        Operation operation = Operation.fromCode(type);
        handlers.get(operation).apply(fruit, quantity);
    }
}

@FunctionalInterface
interface OperationHandler {
    void apply(String fruit, int quantity);
}

