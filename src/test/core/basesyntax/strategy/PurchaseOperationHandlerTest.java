package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static final String PURCHASE_OPERATION = "p";
    private static OperationHandler operationHandler;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initializeFields() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void createStorage() {
        Storage.fruitStorage.put(new Fruit("banana"), 34);
        Storage.fruitStorage.put(new Fruit("orange"), 48);
        Storage.fruitStorage.put(new Fruit("apple"), 100);
    }

    @After
    public void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void apply_FruitIsNotPresent_throwException() {
        expectedException.expect(RuntimeException.class);
        operationHandler.apply(new TransactionDto(PURCHASE_OPERATION, "pear", 88));
    }

    @Test
    public void apply_NotEnoughFruits_throwException() {
        expectedException.expect(RuntimeException.class);
        operationHandler.apply(new TransactionDto(PURCHASE_OPERATION, "banana", 35));
    }

    @Test
    public void apply_EnoughFruits_purchase() {
        Fruit banana = new Fruit("banana");
        operationHandler.apply(new TransactionDto(PURCHASE_OPERATION, banana.getName(), 34));
        int expected = 0;
        int actual = Storage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }
}
