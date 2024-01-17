package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operationhandler.BalanceHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseHandler;
import core.basesyntax.service.operationhandler.ReturnHandler;
import core.basesyntax.service.operationhandler.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Map<Operation, OperationHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(Operation.BALANCE, new BalanceHandler());
        operationStrategies.put(Operation.SUPPLY, new SupplyHandler());
        operationStrategies.put(Operation.PURCHASE,
                new PurchaseHandler());
        operationStrategies.put(Operation.RETURN, new ReturnHandler());
        operationStrategy = new OperationStrategy(operationStrategies);
    }

    @Test
    public void get_rightAction_ok() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        OperationHandler handler = operationStrategy.getHandler(item.getOperation());
        handler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 43);
    }
}
