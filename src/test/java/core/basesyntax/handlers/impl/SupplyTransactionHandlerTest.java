package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.handlers.TransactionHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_QUANTITY = 5;
    private TransactionHandler handler;
    private FruitTransaction validTransaction;
    private FruitStorage storage;

    @Before
    public void setUp() {
        storage = new FruitStorage();
        handler = new SupplyTransactionHandler(new FruitStorageDao(storage));
        validTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, VALID_QUANTITY);
    }

    @Test
    public void handle_validTransactionWithEmptyStorage_quantityOk() {
        handler.handle(validTransaction);
        Integer actual = storage.getStorage().get(APPLE);
        Integer expected = VALID_QUANTITY;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_validTransactions_quantityOk() {
        int times = 3;
        for (int i = 0; i < times; i++) {
            handler.handle(validTransaction);
        }
        Integer actual = storage.getStorage().get(APPLE);
        Integer expected = VALID_QUANTITY * times;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.getStorage().clear();
    }
}
