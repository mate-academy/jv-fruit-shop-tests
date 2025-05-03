package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transaction.BalanceTransactionHandlerImpl;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static TransactionStrategy transactionStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandlerImpl());
        transactionStrategy = new TransactionStrategyImpl(transactionHandlerMap);
    }

    @Test
    void get_balanceHandler_ok() {
        TransactionHandler expected = new BalanceTransactionHandlerImpl();
        TransactionHandler actual = transactionStrategy.get(BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
