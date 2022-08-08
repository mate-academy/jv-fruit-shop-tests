package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static BalanceOperationHandler balanceOperationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static ReturnOperationHandler returnOperationHandler;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        initializeMap();
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_differentOperationHandlers_Ok() {
        OperationHandler expectedBalanceOperationHandler = balanceOperationHandler;
        OperationHandler actualBalanceOperationHandler = strategy
                .get(FruitTransaction.Operation.BALANCE);
        assertEquals("Expected should be equal to "
                + expectedBalanceOperationHandler + " but was: "
                + actualBalanceOperationHandler,
                expectedBalanceOperationHandler,
                actualBalanceOperationHandler);
        OperationHandler expectedPurchaseOperationHandler = purchaseOperationHandler;
        OperationHandler actualPurchaseOperationHandler = strategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertEquals("Expected should be equal to "
                + expectedPurchaseOperationHandler + " but was: "
                + actualPurchaseOperationHandler,
                expectedPurchaseOperationHandler,
                actualPurchaseOperationHandler);
        OperationHandler expectedReturnOperationHandler = returnOperationHandler;
        OperationHandler actualReturnOperationHandler = strategy
                .get(FruitTransaction.Operation.RETURN);
        assertEquals("Expected should be equal to "
                + expectedReturnOperationHandler + " but was: "
                + actualReturnOperationHandler,
                expectedReturnOperationHandler,
                actualReturnOperationHandler);
        OperationHandler expectedSupplyOperationHandler = supplyOperationHandler;
        OperationHandler actualSupplyOperationHandler = strategy
                .get(FruitTransaction.Operation.SUPPLY);
        assertEquals("Expected should be equal to "
                + expectedSupplyOperationHandler + " but was: "
                + actualSupplyOperationHandler,
                expectedSupplyOperationHandler,
                actualSupplyOperationHandler);

    }

    private static void initializeMap() {
        initializeOperationHandlers();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                purchaseOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                returnOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                supplyOperationHandler);
    }

    private static void initializeOperationHandlers() {
        balanceOperationHandler = new BalanceOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
    }
}
