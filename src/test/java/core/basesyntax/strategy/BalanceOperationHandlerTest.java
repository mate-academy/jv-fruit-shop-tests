package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void process_emptyStorageOk() {
        FruitTransfer fruitTransfer = new FruitTransfer(FruitTransfer.Operation.BALANCE,
                new Fruit("banana"), 100);
        balanceOperationHandler.process(fruitTransfer);
        Integer expected = 100;
        Integer actual = Storage.fruits.get(fruitTransfer.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_ok() {
        Storage.fruits.put(new Fruit("banana"), 1);
        FruitTransfer fruitTransfer = new FruitTransfer(FruitTransfer.Operation.BALANCE,
                new Fruit("banana"), 100);
        balanceOperationHandler.process(fruitTransfer);
        Integer expected = 101;
        Integer actual = Storage.fruits.get(fruitTransfer.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
