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
    private static Storage storage;
    private static FruitStorageDaoImpl fruitStorageDao;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        fruitStorageDao = new FruitStorageDaoImpl(storage);
        handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorageDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler expectedBalanceHandler
                = handlerMap.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actualBalanceHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedBalanceHandler, actualBalanceHandler);

        OperationHandler expectedPurchaseHandler
                = handlerMap.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler actualPurchaseHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedPurchaseHandler, actualPurchaseHandler);

        OperationHandler expectedReturnHandler
                = handlerMap.get(FruitTransaction.Operation.RETURN);
        OperationHandler actualReturnHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expectedReturnHandler, actualReturnHandler);

        OperationHandler expectedSupplyHandler
                = handlerMap.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler actualSupplyHandler
                = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedSupplyHandler, actualSupplyHandler);
    }

    @Test
    void applyOperation_nullParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(null));
    }
}
