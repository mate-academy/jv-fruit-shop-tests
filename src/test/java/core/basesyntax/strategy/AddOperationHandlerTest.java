package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final String SUPPLY = "s";
    private static final String RETURN = "r";
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit ORANGE = new Fruit("orange");
    private static OperationHandler addOperation;

    @BeforeClass
    public static void before() {
        addOperation = new AddOperationHandler();
    }

    @Before
    public void beforeEach() {
        Storage.storage.clear();
        Storage.storage.put(APPLE, 20);
        Storage.storage.put(BANANA, 30);
        Storage.storage.put(ORANGE, 40);
    }

    @Test
    public void supplySingle_Ok() {
        int expected = 50;
        Transaction transaction = new Transaction(SUPPLY, APPLE, 30);
        int actual = addOperation.apply(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void supplyMultiple_Ok() {
        final int expectedApple = 80;
        final int expectedBanana = 60;
        final int expectedOrange = 70;

        Transaction transactionApple = new Transaction(SUPPLY, APPLE, 30);
        Transaction transactionOrange = new Transaction(SUPPLY, ORANGE, 30);
        Transaction transactionBanana = new Transaction(SUPPLY, BANANA, 30);

        addOperation.apply(transactionApple);
        addOperation.apply(transactionApple);
        addOperation.apply(transactionOrange);
        addOperation.apply(transactionBanana);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }

    @Test
    public void returnSingle_Ok() {
        int expected = 50;
        Transaction transaction = new Transaction(RETURN, APPLE, 30);
        int actual = addOperation.apply(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void returnMultiple_Ok() {
        final int expectedApple = 80;
        final int expectedBanana = 60;
        final int expectedOrange = 70;

        Transaction transactionApple = new Transaction(RETURN, APPLE, 30);
        Transaction transactionOrange = new Transaction(RETURN, ORANGE, 30);
        Transaction transactionBanana = new Transaction(RETURN, BANANA, 30);

        addOperation.apply(transactionApple);
        addOperation.apply(transactionApple);
        addOperation.apply(transactionOrange);
        addOperation.apply(transactionBanana);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }

    @Test
    public void returnAndSupplyOperations_Ok() {
        final int expectedApple = 140;
        final int expectedBanana = 90;
        final int expectedOrange = 130;

        Transaction transactionReturnApple = new Transaction(RETURN, APPLE, 30);
        Transaction transactionReturnOrange = new Transaction(RETURN, ORANGE, 30);
        Transaction transactionReturnBanana = new Transaction(RETURN, BANANA, 30);

        Transaction transactionSupplyApple = new Transaction(SUPPLY, APPLE, 30);
        Transaction transactionSupplyOrange = new Transaction(SUPPLY, ORANGE, 30);
        Transaction transactionSupplyBanana = new Transaction(SUPPLY, BANANA, 30);

        addOperation.apply(transactionReturnApple);
        addOperation.apply(transactionReturnApple);
        addOperation.apply(transactionReturnOrange);
        addOperation.apply(transactionReturnBanana);
        addOperation.apply(transactionSupplyApple);
        addOperation.apply(transactionSupplyApple);
        addOperation.apply(transactionSupplyBanana);
        addOperation.apply(transactionSupplyOrange);
        addOperation.apply(transactionSupplyOrange);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }
}
