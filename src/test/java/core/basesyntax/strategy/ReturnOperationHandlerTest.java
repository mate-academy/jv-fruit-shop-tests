package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static FruitTransfer fruitTransfer;
    private static Fruit fruit;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        fruit = new Fruit("banana");
    }

    @Test
    public void process_emptyStorageOk() {
        fruitTransfer = new FruitTransfer(FruitTransfer.Operation.BALANCE,
                fruit, 100);
        returnOperationHandler.process(fruitTransfer);
        Integer expected = 100;
        Integer actual = Storage.fruits.get(fruitTransfer.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_ok() {
        Storage.fruits.put(fruit, 1);
        fruitTransfer = new FruitTransfer(FruitTransfer.Operation.BALANCE,
                fruit, 100);
        returnOperationHandler.process(fruitTransfer);
        Integer expected = 101;
        Integer actual = Storage.fruits.get(fruitTransfer.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
