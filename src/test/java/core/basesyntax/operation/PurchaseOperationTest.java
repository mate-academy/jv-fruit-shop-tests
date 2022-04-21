package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;
    private static FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        purchaseOperation = new BalanceOperation(fruitShopService);
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test
    public void handle_Ok() {
        purchaseOperation.handle(fruitTransaction);
        Assert.assertEquals(10, (int) Storage.storage.get("banana"));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
