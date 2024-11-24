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

    public void execute(FruitTransaction transaction, FruitDB fruitDB) {
        String type = transaction.getType();
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        if (quantity < 0) {
            throw new IllegalArgumentException("Transaction quantity cannot be negative");
        }

        Operation operation = Operation.fromCode(type);
        OperationHandler handler = handlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("Invalid operation type: " + type);
        }
        handler.apply(fruit, quantity);
    }
}
