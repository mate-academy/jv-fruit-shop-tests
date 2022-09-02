package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static final OperationHandler operationHandler = new PurchaseOperationHandler();
    private static Transaction transaction;
    private static final Map<Fruit, Integer> storage = Storage.getAll();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        transaction = new Transaction("p", new Fruit("banana"), null);
    }

    @Test
    public void applyPurchaseOperationHandler_getFruitBanana_isValid() {
        Fruit expected = new Fruit("banana");
        Fruit actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void applyPurchaseOperationHandler_getFruitApple_isValid() {
        transaction.getFruit().setName("apple");
        Fruit expected = new Fruit("apple");
        Fruit actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void applyPurchaseOperationHandler_currentQuantity15_isValid() {
        storage.put(new Fruit("banana"),15);
        Integer expected = 15;
        Integer actual = storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void applyPurchaseOperationHandler_currentQuantityNull_isNotValid() {
        transaction.setQuantity(null);
        exceptionRule.expect(NoSuchElementException.class);
        exceptionRule.expectMessage("Quantity of fruits is not enough for purchase");
        operationHandler.apply(transaction);
    }

    @Test
    public void applyPurchaseOperationHandler_currentQuantityIsNegative_isNotValid() {
        transaction.setQuantity(-1);
        exceptionRule.expect(NoSuchElementException.class);
        exceptionRule.expectMessage("Quantity of fruits is not enough for purchase");
        operationHandler.apply(transaction);
    }

    @Test
    public void applyPurchaseOperationHandler_Apple15_isValid() {
        storage.put(new Fruit("apple"), 20);
        operationHandler.apply(new Transaction("p", new Fruit("apple"), 5));
        Integer expected = 15;
        Integer actual = storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void applyPurchaseOperationHandler_AppleIs0_isValid() {
        storage.put(new Fruit("apple"), 5);
        operationHandler.apply(new Transaction("p", new Fruit("apple"), 5));
        Integer expected = 0;
        Integer actual = storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
