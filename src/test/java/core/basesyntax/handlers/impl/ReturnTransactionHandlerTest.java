package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.handlers.TransactionHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_QUANTITY = 5;
    private TransactionHandler handler;
    private FruitTransaction validTransaction;
    private Map<String, Integer> storageMap;

    @Before
    public void setUp() {
        FruitStorage storage = new FruitStorage();
        storageMap = storage.getStorage();
        handler = new ReturnTransactionHandler(new FruitStorageDao(storage));
        validTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, APPLE, VALID_QUANTITY);
    }

    @Test
    public void handle_validTransactionWithEmptyStorage_quantityOk() {
        handler.handle(validTransaction);
        Integer actual = storageMap.get(APPLE);
        Integer expected = VALID_QUANTITY;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_validTransactions_quantityOk() {
        int times = 3;
        for (int i = 0; i < times; i++) {
            handler.handle(validTransaction);
        }
        Integer actual = storageMap.get(APPLE);
        Integer expected = VALID_QUANTITY * times;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageMap.clear();
    }
}
