package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static OperationHandler supplyOperation;
    private static FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        supplyOperation = new SupplyOperation(fruitShopService);
        Storage.storage.put("banana", 20);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test
    public void handle_Ok() {
        supplyOperation.handle(fruitTransaction);
        Assert.assertEquals(30, (int) Storage.storage.get("banana"));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
