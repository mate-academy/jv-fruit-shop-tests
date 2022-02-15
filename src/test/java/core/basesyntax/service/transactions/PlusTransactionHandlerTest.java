package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlusTransactionHandlerTest {
    private static final TransactionHandler HANDLER = new PlusTransactionHandler();
    private static final Transaction SUPPLY_TRANSACTION_1 = new Transaction(
            Transaction.TransactionType.SUPPLY,
            new Fruit(Fruit.FruitType.APPLE), 20);
    private static final Transaction SUPPLY_TRANSACTION_2 = new Transaction(
            Transaction.TransactionType.SUPPLY,
            new Fruit(Fruit.FruitType.APPLE), 50);
    private static final Transaction RETURN_TRANSACTION_1 = new Transaction(
            Transaction.TransactionType.RETURN,
            new Fruit(Fruit.FruitType.APPLE), 20);
    private static final Transaction RETURN_TRANSACTION_2 = new Transaction(
            Transaction.TransactionType.RETURN,
            new Fruit(Fruit.FruitType.APPLE), 50);
    private static final Fruit APPLE = new Fruit(Fruit.FruitType.APPLE);

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void processSupplyTransaction_Ok() {
        HANDLER.processTransaction(SUPPLY_TRANSACTION_1);
        int expected = 20;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void processSeveralSupplyTransactions_Ok() {
        HANDLER.processTransaction(SUPPLY_TRANSACTION_1);
        HANDLER.processTransaction(SUPPLY_TRANSACTION_2);
        int expected = 70;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void processReturnTransaction_Ok() {
        HANDLER.processTransaction(RETURN_TRANSACTION_1);
        int expected = 20;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void processSeveralReturnTransactions_Ok() {
        HANDLER.processTransaction(RETURN_TRANSACTION_1);
        HANDLER.processTransaction(RETURN_TRANSACTION_2);
        int expected = 70;
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}