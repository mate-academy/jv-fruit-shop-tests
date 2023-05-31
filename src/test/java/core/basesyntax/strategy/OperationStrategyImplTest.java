package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.operationwithfruits.BalanceOperationHandler;
import core.basesyntax.service.operationwithfruits.OperationHandler;
import core.basesyntax.service.operationwithfruits.PurchaseOperationHandler;
import core.basesyntax.service.operationwithfruits.ReturnOperationHandler;
import core.basesyntax.service.operationwithfruits.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static StorageDao storageDao;
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static OperationHandler supplyOperationHandler;

    private static Map<FruitTransaction.Operation, OperationHandler> strategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        strategy = new HashMap<>();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        strategy.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        strategy.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        strategy.put(FruitTransaction.Operation.RETURN, returnOperationHandler);
        strategy.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        operationStrategy = new OperationStrategyImpl(strategy);
    }

    @Test
    public void operationHandlerBalance_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(actual, balanceOperationHandler);
    }

    @Test
    public void operationHandlerPurchase_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(actual, purchaseOperationHandler);
    }

    @Test
    public void operationHandlerReturn_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(actual, returnOperationHandler);
    }

    @Test
    public void operationHandlerSupply_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(actual, supplyOperationHandler);
    }

    @Test
    public void operationHandlerNull_NotOk() {
        OperationHandler actual = operationStrategy.get(null);
        assertNull(actual);
    }
}
