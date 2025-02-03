package core.basesyntax.strategy;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperationStrategyImplTest {
    private static final Storage STORAGE = new Storage();
    private static final Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLERS
            = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        OPERATION_HANDLERS.clear();
        OPERATION_HANDLERS.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(STORAGE));
    }

    @AfterEach
    public void tearDown() {
        STORAGE.getInventory().clear();
    }

    @Test
    public void execute_validData_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("Mango");
        fruitTransaction.setQuantity(15);
        OperationHandler operationHandler = OPERATION_HANDLERS.get(fruitTransaction.getOperation());
        operationHandler.handle(fruitTransaction);

        assertTrue(STORAGE.getInventory().containsKey("Mango"));
    }

    @Test
    public void execute_invalidNullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = null;
        OperationHandler nullOperationHandler =
                OPERATION_HANDLERS.get(FruitTransaction.Operation.BALANCE);

        assertThrows(RuntimeException.class,
                () -> nullOperationHandler.handle(fruitTransaction),
                "Fruit transaction cannot be null");
    }
}
