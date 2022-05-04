package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler(storageDao));
        operationHandlerMap.put("p", new PurchaseOperationHandler(storageDao));
        operationHandlerMap.put("r", new ReturnOperationHandler(storageDao));
        operationHandlerMap.put("s", new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_purchaseClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("p").getClass();
        Assert.assertEquals(PurchaseOperationHandler.class, actual);
    }

    @Test
    public void get_supplyClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("s").getClass();
        Assert.assertEquals(SupplyOperationHandler.class, actual);
    }

    @Test
    public void get_returnClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("r").getClass();
        Assert.assertEquals(ReturnOperationHandler.class, actual);
    }

    @Test
    public void get_balanceClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("b").getClass();
        Assert.assertEquals(BalanceOperationHandler.class, actual);
    }

    @Test
    public void get_invalidInputValue_NotOk() {
        Assert.assertNull(operationStrategy.get("invalid"));
    }
}
