package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private Fruit defaultFruit;
    private FruitTransaction defaultTransaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        defaultFruit = new Fruit("banana");
        defaultTransaction = new FruitTransaction();
        defaultTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        defaultTransaction.setFruit(defaultFruit);
        defaultTransaction.setQuantity(0);
    }

    @Test
    public void balance_validData_Ok() {
        Storage.storage.put(defaultFruit, 10);
        defaultTransaction.setQuantity(5);
        operationHandler.apply(defaultTransaction);
        Assert.assertEquals("Expected another value: 15",
                Integer.valueOf(15),
                Storage.storage.get(defaultFruit));
    }

    @Test
    public void balance_ProductNotInStorage_Ok() {
        defaultTransaction.setQuantity(15);
        operationHandler.apply(defaultTransaction);
        Assert.assertEquals("Expected another value: 15",
                Integer.valueOf(15),
                Storage.storage.get(defaultFruit));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
