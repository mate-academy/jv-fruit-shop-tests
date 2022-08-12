package core.basesyntax.strategy;

import static core.basesyntax.strategy.OperationHandlersUtil.BALANCE_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.PURCHASE_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.RETURN_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.SUPPLY_OPERATION_HANDLER;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        initializeMap();
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_Ok() {
        OperationHandler expectedBalanceOperationHandler = BALANCE_OPERATION_HANDLER;
        OperationHandler actualBalanceOperationHandler = strategy
                .get(FruitTransaction.Operation.BALANCE);
        assertEquals("get should return BalanceOperationHandler: "
                + expectedBalanceOperationHandler + " but was: "
                + actualBalanceOperationHandler,
                expectedBalanceOperationHandler,
                actualBalanceOperationHandler);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        OperationHandler expectedPurchaseOperationHandler = PURCHASE_OPERATION_HANDLER;
        OperationHandler actualPurchaseOperationHandler = strategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertEquals("get should return PurchaseOperationHandler: "
                + expectedPurchaseOperationHandler + " but was: "
                + actualPurchaseOperationHandler,
                expectedPurchaseOperationHandler,
                actualPurchaseOperationHandler);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        OperationHandler expectedReturnOperationHandler = RETURN_OPERATION_HANDLER;
        OperationHandler actualReturnOperationHandler = strategy
                .get(FruitTransaction.Operation.RETURN);
        assertEquals("get should return ReturnOperationHandler: "
                + expectedReturnOperationHandler + " but was: "
                + actualReturnOperationHandler,
                expectedReturnOperationHandler,
                actualReturnOperationHandler);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        OperationHandler expectedSupplyOperationHandler = SUPPLY_OPERATION_HANDLER;
        OperationHandler actualSupplyOperationHandler = strategy
                .get(FruitTransaction.Operation.SUPPLY);
        assertEquals("get should return SupplyOperationHandler: "
                + expectedSupplyOperationHandler + " but was: "
                + actualSupplyOperationHandler,
                expectedSupplyOperationHandler,
                actualSupplyOperationHandler);
    }

    private static void initializeMap() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                BALANCE_OPERATION_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                PURCHASE_OPERATION_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                RETURN_OPERATION_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                SUPPLY_OPERATION_HANDLER);
    }

}
