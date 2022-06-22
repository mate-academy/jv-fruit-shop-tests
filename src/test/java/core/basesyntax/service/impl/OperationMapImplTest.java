package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationMap;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationMapImplTest {
    private static OperationMap operationMap;

    @BeforeClass
    public static void beforeClass() {
        ShopDao shopDao = new ShopDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.RETURN, new ReturnHandler(shopDao));
        operationMap = new OperationMapImpl(operationHandler);
    }

    @Test
    public void get_balanceHandler_ok() {
        assertEquals(BalanceHandler.class,
                operationMap.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void get_supplyHandler_ok() {
        assertEquals(SupplyHandler.class,
                operationMap.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    public void get_purchaseHandler_ok() {
        assertEquals(PurchaseHandler.class,
                operationMap.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void get_returnHandler_ok() {
        assertEquals(ReturnHandler.class,
                operationMap.get(FruitTransaction.Operation.RETURN).getClass());
    }
}
