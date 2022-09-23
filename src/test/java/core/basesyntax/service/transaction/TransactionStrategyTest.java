package core.basesyntax.service.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionStrategyTest {
    private static final Map<FruitTransaction.Operation, TransactionHandler> 
            transactionHandlerMap = new HashMap<>();

    @Before
    public void setUp() {
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
    }

    @Test(expected = RuntimeException.class)
    public void get_nullHandlerMap_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(null);
        TransactionHandler transactionHandler =
                transactionStrategy.get(FruitTransaction.Operation.BALANCE);
    }

    @Test
    public void get_notExistKey_notOk() {
        TransactionStrategy transactionStrategy =
                new TransactionStrategy(transactionHandlerMap);
        TransactionHandler transactionHandler =
                transactionStrategy.get(FruitTransaction.Operation.RETURN);
        assertNull(transactionHandler);
    }

    @Test(expected = RuntimeException.class)
    public void get_nullValue_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(transactionHandlerMap);
        TransactionHandler transactionHandler = transactionStrategy.get(null);
    }

    @Test
    public void get_value_ok() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(transactionHandlerMap);
        TransactionHandler transactionHandler =
                transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(transactionHandler.getClass(), BalanceTransactionHandler.class);
    }
}
