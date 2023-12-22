package core.basesyntax.context;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationContextTest {
    private OperationContext operationContext;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        operationContext = new OperationContext(fruitStorage);
    }

    @Test
    void get_operationBalance_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        OperationHandler expectedHandler = new BalanceOperationHandler(fruitStorage);

        OperationHandler actualHandler = operationContext.getOperation(operation);

        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void get_purchaseBalance_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        OperationHandler expectedHandler = new PurchaseOperationHandler(fruitStorage);

        OperationHandler actualHandler = operationContext.getOperation(operation);

        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void get_returnBalance_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        OperationHandler expectedHandler = new ReturnOperationHandler(fruitStorage);

        OperationHandler actualHandler = operationContext.getOperation(operation);

        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void get_supplyBalance_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        OperationHandler expectedHandler = new SupplyOperationHandler(fruitStorage);

        OperationHandler actualHandler = operationContext.getOperation(operation);

        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
