package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Map<Operation, OperationHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(Operation.BALANCE, new BalanceHandler());
        operationStrategies.put(Operation.SUPPLY, new SupplyHandler());
        operationStrategies.put(Operation.PURCHASE,
                new PurchaseHandler());
        operationStrategies.put(Operation.RETURN, new ReturnHandler());
        operationHandler = new OperationStrategy(operationStrategies)
                .getHandler(Operation.BALANCE);
    }

    @Test
    public void get_rightAction_Ok() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        operationHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 43);
    }
}
