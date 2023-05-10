package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.Warehouse;
import core.basesyntax.service.WarehouseImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationReturnTest {
    private static final String TEST_FRUIT = "banana";
    private static Warehouse warehouse;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new OperationBalance());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new OperationPurchase());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new OperationReturn());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new OperationSupply());
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        warehouse = new WarehouseImpl(fruitTransactionList, strategy);
    }

    @AfterEach
    void tearDown() {
        warehouse.getDayOperations().clear();
        warehouse.getRemains().clear();
    }

    @Test
    void warehouseOperation_normalBehavior_ok() {
        OperationReturn operationReturn = new OperationReturn();
        int expected = 50;
        int actual = operationReturn.warehouseOperation(TEST_FRUIT, expected, warehouse);
        assertEquals(actual, expected);
    }

    @Test
    void warehouseOperation_fruitWareHouseNull_notOk() {
        OperationReturn operationReturn = new OperationReturn();
        int returnQuantity = 50;
        Assertions.assertThrows(RuntimeException.class,
                () -> operationReturn.warehouseOperation(null, returnQuantity, warehouse));
        Assertions.assertThrows(RuntimeException.class,
                () -> operationReturn.warehouseOperation(null, returnQuantity, null));
        Assertions.assertThrows(RuntimeException.class,
                () -> operationReturn.warehouseOperation(TEST_FRUIT, returnQuantity, null));

    }
}
