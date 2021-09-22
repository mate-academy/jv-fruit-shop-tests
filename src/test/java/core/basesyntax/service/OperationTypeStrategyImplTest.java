package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationType;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationTypeStrategy;
import core.basesyntax.operation.OperationTypeStrategyImpl;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.ShopOperationHandler;
import core.basesyntax.operation.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTypeStrategyImplTest {
    private static Map<OperationType, ShopOperationHandler> shopOperationMap;
    private static OperationTypeStrategy operationTypeStrategy;

    @BeforeClass
    public static void setUp() {
        shopOperationMap = new HashMap<>();
        shopOperationMap.put(OperationType.BALANCE, new BalanceHandler());
        shopOperationMap.put(OperationType.PURCHASE, new PurchaseHandler());
        shopOperationMap.put(OperationType.SUPPLY, new SupplyHandler());
        shopOperationMap.put(OperationType.RETURN, new ReturnHandler());
        operationTypeStrategy = new OperationTypeStrategyImpl(shopOperationMap);
    }

    @Test
    public void get_CheckValidOperationType_Ok() {
        Class<? extends ShopOperationHandler> expected = SupplyHandler.class;
        Class<? extends ShopOperationHandler> actual = operationTypeStrategy
                .get(OperationType.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void get_CheckInvalidOperationType_NotOk() {
        OperationType operationType = OperationType.valueOf("InvalidOperation");
        new OperationTypeStrategyImpl(shopOperationMap).get(operationType);
    }

    @Test (expected = RuntimeException.class)
    public void get_CheckEmptyOperationType_NotOk() {
        OperationType operationType = OperationType.valueOf(" ");
        new OperationTypeStrategyImpl(shopOperationMap).get(operationType);
    }
}
