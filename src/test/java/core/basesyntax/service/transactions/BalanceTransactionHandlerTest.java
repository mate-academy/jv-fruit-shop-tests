package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceTransactionHandlerTest {
    private static final TransactionHandler HANDLER = new BalanceTransactionHandler();
    private static final Transaction BALANCE_TRANSACTION_1 = new Transaction(
            Transaction.TransactionType.BALANCE,
            new Fruit(Fruit.FruitType.APPLE), 20);
    private static final Transaction BALANCE_TRANSACTION_2 = new Transaction(
            Transaction.TransactionType.BALANCE,
            new Fruit(Fruit.FruitType.APPLE), 50);
    private static final Fruit APPLE = new Fruit(Fruit.FruitType.APPLE);

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void processTransaction_Ok() {
        HANDLER.processTransaction(BALANCE_TRANSACTION_1);
        int expected = 20;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void processSeveralTransactions_Ok() {
        HANDLER.processTransaction(BALANCE_TRANSACTION_1);
        HANDLER.processTransaction(BALANCE_TRANSACTION_2);
        int expected = 50;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
