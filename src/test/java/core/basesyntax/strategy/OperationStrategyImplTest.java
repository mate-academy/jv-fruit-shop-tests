package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operation.BalanceOperation;
import core.basesyntax.service.impl.operation.OperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperation;
import core.basesyntax.service.impl.operation.ReturnOperation;
import core.basesyntax.service.impl.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static FruitStorageDaoImpl fruitStorageDao;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Storage storage = new Storage(new HashMap<>());
        fruitStorageDao = new FruitStorageDaoImpl(storage);
        operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorageDao)));
    }

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler expectedBalanceHandler = new BalanceOperation(fruitStorageDao);
        OperationHandler actualBalanceHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedBalanceHandler.getClass(), actualBalanceHandler.getClass());

        OperationHandler expectedPurchaseHandler = new PurchaseOperation(fruitStorageDao);
        OperationHandler actualPurchaseHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedPurchaseHandler.getClass(), actualPurchaseHandler.getClass());

        OperationHandler expectedReturnHandler = new ReturnOperation(fruitStorageDao);
        OperationHandler actualReturnHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expectedReturnHandler.getClass(), actualReturnHandler.getClass());

        OperationHandler expectedSupplyHandler = new SupplyOperation(fruitStorageDao);
        OperationHandler actualSupplyHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedSupplyHandler.getClass(), actualSupplyHandler.getClass());
    }

    @Test
    void applyOperation_nullParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(null));
    }
}
