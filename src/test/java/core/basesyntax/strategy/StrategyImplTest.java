package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceHandlerImpl;
import core.basesyntax.strategy.handler.PurchaseHandlerImpl;
import core.basesyntax.strategy.handler.ReturnHandlerImpl;
import core.basesyntax.strategy.handler.SupplyHandlerImpl;
import core.basesyntax.strategy.handler.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static Strategy strategy;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandlerImpl(storageDao));
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandlerImpl(storageDao));
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandlerImpl(storageDao));
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandlerImpl(storageDao));
        strategy = new StrategyImpl(transactionHandlerMap);
    }

    @Test
    public void get_balanceTransactionHandler_Ok() {
        Class expected = BalanceHandlerImpl.class;
        Class actual = strategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyTransactionHandler_Ok() {
        Class expected = SupplyHandlerImpl.class;
        Class actual = strategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnTransactionHandler_Ok() {
        Class expected = ReturnHandlerImpl.class;
        Class actual = strategy.get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseTransactionHandler_Ok() {
        Class expected = PurchaseHandlerImpl.class;
        Class actual = strategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_nullValue_Ok() {
        TransactionHandler actual = strategy.get(null);
        assertNull(actual);
    }
}
