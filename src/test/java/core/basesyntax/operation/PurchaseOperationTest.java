package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        operationHandler = new BalanceOperation(fruitShopService);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void handle_Ok() {
        Storage.storage.put("banana", 20);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(10, (int) Storage.storage.get("banana"));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
