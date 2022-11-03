package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.handlers.TransactionHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseTransactionHandlerTest {
    private static final String NOT_ENOUGH_FRUITS = "Not enough fruits in the storage";
    private static final String NO_SUCH_FRUIT = "No such fruit in the storage";
    private static final String APPLE = "apple";
    private static final int VALID_QUANTITY = 5;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private TransactionHandler handler;
    private FruitTransaction validTransaction;
    private Map<String, Integer> storageMap;

    @Before
    public void setUp() {
        FruitStorage storage = new FruitStorage();
        storageMap = storage.getStorage();
        handler = new PurchaseTransactionHandler(new FruitStorageDao(storage));
        validTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, VALID_QUANTITY);
    }

    @Test
    public void handle_purchaseLessThanExists_ok() {
        storageMap.put(APPLE, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 4);
        handler.handle(transaction);
        int actual = storageMap.get(APPLE);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_purchaseAllExisting_ok() {
        storageMap.put(APPLE, VALID_QUANTITY);
        handler.handle(validTransaction);
        int actual = storageMap.get(APPLE);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_purchaseMoreThanExists_notOk() {
        storageMap.put(APPLE, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 6);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(NOT_ENOUGH_FRUITS);
        handler.handle(transaction);
    }

    @Test
    public void handle_purchaseNotExisting_notOk() {
        storageMap.put(APPLE, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", VALID_QUANTITY);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(NO_SUCH_FRUIT);
        handler.handle(transaction);
    }

    @After
    public void tearDown() {
        storageMap.clear();
    }
}
