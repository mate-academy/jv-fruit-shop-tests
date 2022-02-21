package fruite.store.service.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StrategyHandler {
    public static final Map<String, OperationHandler> operationTypeStrategy = new HashMap<>();

    public boolean doSpecialOperationOnFruits(String type, String fruit, String quantity) {
        validateType(type);
        validateFruit(fruit);
        validateQuantity(quantity);
        Integer intQuantity = Integer.parseInt(quantity);
        for (Map.Entry<String, OperationHandler> entry : operationTypeStrategy.entrySet()) {
            if (entry.getKey().equals(type)) {
                entry.getValue().doOperation(fruit, intQuantity);
                return true;
            }
        }
        throw new RuntimeException("Unknown operation: " + type);
    }

    private void validateType(String type) {
        type = type.toLowerCase();
        if(!Pattern.matches("[a-z]{1,}", type)) {
            throw new RuntimeException("Invalid operation type: " + type);
        }
    }

    private void validateFruit(String fruit) {
        fruit = fruit.toLowerCase();
        if(!Pattern.matches("[a-z]{2,}", fruit)) {
            throw new RuntimeException("Invalid fruit name: " + fruit);
        }
    }

    private void validateQuantity(String quantity) {
        if(!Pattern.matches("\\d+", quantity)) {
            throw new RuntimeException("Invalid quantity: " + quantity);
        }
    }
}
