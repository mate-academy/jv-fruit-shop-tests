package core.basesyntax.service.transactions.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.transactions.BalanceTransactionHandler;
import core.basesyntax.service.transactions.MinusTransactionHandler;
import core.basesyntax.service.transactions.PlusTransactionHandler;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static final TransactionStrategy STRATEGY = new TransactionStrategyImpl();

    @Test
    public void getTransactionHandlers_Ok() {
        assertEquals(BalanceTransactionHandler.class,
                STRATEGY.getTransactionHandler(Transaction.TransactionType.BALANCE).getClass());
        assertEquals(MinusTransactionHandler.class,
                STRATEGY.getTransactionHandler(Transaction.TransactionType.PURCHASE).getClass());
        assertEquals(PlusTransactionHandler.class,
                STRATEGY.getTransactionHandler(Transaction.TransactionType.SUPPLY).getClass());
        assertEquals(PlusTransactionHandler.class,
                STRATEGY.getTransactionHandler(Transaction.TransactionType.RETURN).getClass());
    }
}
