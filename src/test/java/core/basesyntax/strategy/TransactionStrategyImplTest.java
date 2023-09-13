package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transaction.BalanceTransactionHandler;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;
    private static TransactionStrategy transactionStrategy;

    @BeforeAll
    static void beforeAll() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionStrategy = new TransactionStrategyImpl(transactionHandlerMap);
    }

    @Test
    void get_balanceHandler_ok() {
        TransactionHandler expected = new BalanceTransactionHandler();
        TransactionHandler actual = transactionStrategy.get(BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
