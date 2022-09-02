package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTester {
    private static Storage fruitStorage;
    private static OperationHandler handler;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void setup() {
        fruitStorage = new Storage();
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void purchase_operation_fruit_absent_exception() {
        Transaction transaction = new Transaction("p", new Fruit("banana"), 20);
        exceptionRule.expect(NoSuchElementException.class);
        exceptionRule.expectMessage("Not enough fruits for purchase");
        handler.apply(transaction);

    }

    @Test
    public void purchase_operation_fruit_lack_exceptionK() {
        fruitStorage.put(new Fruit("banana"), 30);
        Transaction transaction = new Transaction("p", new Fruit("banana"), 50);
        exceptionRule.expect(NoSuchElementException.class);
        exceptionRule.expectMessage("Not enough fruits for purchase");
        handler.apply(transaction);

    }

    @Test
    public void purchase_operation_fruit_present_OK() {
        fruitStorage.put(new Fruit("apple"), 40);
        Transaction transaction = new Transaction("p", new Fruit("apple"), 20);
        handler.apply(transaction);
        Integer expected = 20;
        Integer actual = fruitStorage.get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void purchase_all_operation_fruit_present_OK() {
        fruitStorage.put(new Fruit("kiwi"), 50);
        Transaction transaction = new Transaction("p", new Fruit("kiwi"), 50);
        handler.apply(transaction);
        Integer expected = 0;
        Integer actual = fruitStorage.get(new Fruit("kiwi"));
        Assert.assertEquals(expected, actual);

    }

    @After
    public void tearDown() {
        fruitStorage.clear();
    }
}
