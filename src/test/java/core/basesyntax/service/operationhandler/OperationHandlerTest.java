package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private static final Map<Operation, OperationHandler> OPERATION_STRATEGIES = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.SUPPLY, new SupplyHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.RETURN, new ReturnHandler()
    );

    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        operationHandler = new OperationStrategy(OPERATION_STRATEGIES)
                .getHandler(Operation.BALANCE);
    }

    @Test
    public void get_rightAction_ok() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        operationHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 43);
    }

    @Test
    public void get_wrongAction_notOk() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        operationHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertNotEquals(quantity, 42);
    }
}
