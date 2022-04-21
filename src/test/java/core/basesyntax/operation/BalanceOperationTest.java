package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        fruitTransaction = new FruitTransaction();
        operationHandler = new BalanceOperation(fruitShopService);
    }

    @Test
    public void handle_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationHandler.handle(fruitTransaction);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
