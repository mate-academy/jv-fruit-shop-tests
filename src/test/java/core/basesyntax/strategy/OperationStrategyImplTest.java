package core.basesyntax.strategy;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void getHandlerByOperation_balanceOperation_ok() {
        OperationHandler handler
                = operationStrategy.getHandlerByOperation(FruitTransaction.Operation.BALANCE);
        assertThat(handler, instanceOf(BalanceOperationHandler.class));
    }

    @Test
    public void getHandlerByOperation_purchaseOperation_ok() {
        OperationHandler handler
                = operationStrategy.getHandlerByOperation(FruitTransaction.Operation.PURCHASE);
        assertThat(handler, instanceOf(PurchaseOperationHandler.class));
    }

    @Test
    public void getHandlerByOperation_returnOperation_ok() {
        OperationHandler handler
                = operationStrategy.getHandlerByOperation(FruitTransaction.Operation.RETURN);
        assertThat(handler, instanceOf(ReturnOperationHandler.class));
    }

    @Test
    public void getHandlerByOperation_supplyOperation_ok() {
        OperationHandler handler
                = operationStrategy.getHandlerByOperation(FruitTransaction.Operation.SUPPLY);
        assertThat(handler, instanceOf(SupplyOperationHandler.class));
    }

    @Test
    public void getHandlerByOperation_null_ok() {
        OperationHandler handler = operationStrategy.getHandlerByOperation(null);
        assertNull(handler);
    }
}
