package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyTest {
    private static final Map<FruitTransaction.Operation, TransactionHandler> 
            transactionHandlerMap = new HashMap<>();

    @BeforeAll
    static void setUp() {
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
    }

    @Test
    void get_nullHandlerMap_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(null);
        assertThrows(RuntimeException.class, () -> {
            TransactionHandler transactionHandler =
                    transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        });
    }

    @Test
    void get_notExistKey_notOk() {
        TransactionStrategy transactionStrategy =
                new TransactionStrategy(transactionHandlerMap);
        TransactionHandler transactionHandler =
                transactionStrategy.get(FruitTransaction.Operation.RETURN);
        assertNull(transactionHandler);
    }

    @Test
    void get_nullValue_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(transactionHandlerMap);
        assertThrows(RuntimeException.class, () -> {
            TransactionHandler transactionHandler = transactionStrategy.get(null);
        });
    }

    @Test
    void get_value_ok() {
        TransactionStrategy transactionStrategy = new TransactionStrategy(transactionHandlerMap);
        TransactionHandler transactionHandler =
                transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(transactionHandler.getClass(), BalanceTransactionHandler.class);
    }
}
