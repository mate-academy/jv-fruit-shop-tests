package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        Storage.fruits.clear();
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void handle_quantityNull_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 44);
        supplyOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assert.assertEquals(quantity, 44);
    }

    @Test
    public void handle_quantityNotNull_Ok() {
        Storage.fruits.put("banana", 7);
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        supplyOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assert.assertEquals(quantity, 50);
    }
}
