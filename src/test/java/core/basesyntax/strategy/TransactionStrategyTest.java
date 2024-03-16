package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.TransactionStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transaction.BalanceTransactionHandler;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeAll
    static void beforeAll() {
        transactionStrategy =
                new TransactionStrategyImpl(Map.of(FruitTransaction.Operation.BALANCE,
                        new BalanceTransactionHandler()));
    }

    @Test
    void get_IsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> transactionStrategy.get(null),
                "Test failed! Exception should be thrown if Operation is Null");
    }

    @Test
    void get_OperationNotExist_NOk() {
        FruitTransaction.Operation notExist = FruitTransaction.Operation.RETURN;
        assertThrows(RuntimeException.class, () -> transactionStrategy.get(notExist),
                "Test failed! Exception should be thrown if Operation isn't exist");
    }

    @Test
    void get_ExistOperation_Ok() {
        TransactionHandler expected = new BalanceTransactionHandler();
        TransactionHandler actual = transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass(), "Class must have a name - Balance");
    }
}
