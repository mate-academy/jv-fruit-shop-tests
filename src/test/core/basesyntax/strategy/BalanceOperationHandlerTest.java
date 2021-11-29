package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String BALANCE_OPERATION = "b";
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void initializeFields() {
        operationHandler = new BalanceOperationHandler();
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
    public void apply_fruitIsNotPresent_addEntry() {
        Fruit pear = new Fruit("pear");
        operationHandler.apply(new TransactionDto(BALANCE_OPERATION, pear.getName(), 88));
        int expectedPearAmount = 88;
        int actualPearAmount = Storage.fruitStorage.get(pear);
        Assert.assertEquals(expectedPearAmount, actualPearAmount);
    }

    @Test
    public void apply_fruitIsPresent_reinstallAmount() {
        Fruit banana = new Fruit("banana");
        operationHandler.apply(new TransactionDto(BALANCE_OPERATION, banana.getName(), 100));
        int expectedPearAmount = 100;
        int actualPearAmount = Storage.fruitStorage.get(banana);
        Assert.assertEquals(expectedPearAmount, actualPearAmount);
    }
}
