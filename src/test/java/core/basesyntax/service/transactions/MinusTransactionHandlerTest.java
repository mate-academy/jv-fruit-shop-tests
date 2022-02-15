package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinusTransactionHandlerTest {
    private static final TransactionHandler BALANCE_HANDLER = new BalanceTransactionHandler();
    private static final TransactionHandler MINUS_HANDLER = new MinusTransactionHandler();
    private static final Transaction BALANCE_TRANSACTION = new Transaction(
            Transaction.TransactionType.BALANCE,
            new Fruit(Fruit.FruitType.APPLE), 30);
    private static final Transaction PURCHASE_TRANSACTION = new Transaction(
            Transaction.TransactionType.PURCHASE,
            new Fruit(Fruit.FruitType.APPLE), 20);
    private static final Fruit APPLE = new Fruit(Fruit.FruitType.APPLE);

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void processSupplyTransaction_Ok() {
        BALANCE_HANDLER.processTransaction(BALANCE_TRANSACTION);
        MINUS_HANDLER.processTransaction(PURCHASE_TRANSACTION);
        int expected = 10;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void processSeveralSupplyTransactions_Ok() {
        BALANCE_HANDLER.processTransaction(BALANCE_TRANSACTION);
        MINUS_HANDLER.processTransaction(PURCHASE_TRANSACTION);
        MINUS_HANDLER.processTransaction(PURCHASE_TRANSACTION);
        int expected = 10;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}