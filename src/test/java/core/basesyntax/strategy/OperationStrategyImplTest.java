package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import core.basesyntax.model.operation.BalanceOperationHandler;
import core.basesyntax.model.operation.PurchaseOperationHandler;
import core.basesyntax.model.operation.ReturnOperationHandler;
import core.basesyntax.model.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static final Storage storage = new Storage();
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>();
    private static final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlers);

    @BeforeAll
    public static void setUp() {
        operationHandlers.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storage));
    }

    @AfterEach
    public void tearDown() {
        storage.getInventory().clear();
    }

    @Test
    public void execute_validData_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("Mango");
        fruitTransaction.setQuantity(15);
        operationStrategy.execute(fruitTransaction);

        assertTrue(storage.getInventory().containsKey("Mango"));
    }

    @Test
    public void execute_invalidNullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = null;

        assertThrows(RuntimeException.class,
                () -> operationStrategy.execute(fruitTransaction),
                "Fruit transaction cannot be null");
    }
}
