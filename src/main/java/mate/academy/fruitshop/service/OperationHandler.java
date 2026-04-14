package mate.academy.fruitshop.service;

import mate.academy.fruitshop.model.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    void process(FruitTransaction tx, Map<String, Integer> storage);
}
