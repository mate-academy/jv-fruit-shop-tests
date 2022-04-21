package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private static FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        balanceOperation = new BalanceOperation(fruitShopService);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
    }

    @Test
    public void handle_Ok() {
        balanceOperation.handle(fruitTransaction);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
