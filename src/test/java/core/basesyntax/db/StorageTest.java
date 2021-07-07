package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;

public class StorageTest {
    public static final Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
    public static final Map<Fruit, Integer> storage = new HashMap<>();

    static {
        operationHandlerMap.put("b", new BalanceHandler(storage));
        operationHandlerMap.put("p", new PurchaseHandler(storage));
        operationHandlerMap.put("s", new AdditionHandler(storage));
        operationHandlerMap.put("r", new AdditionHandler(storage));
    }
}
