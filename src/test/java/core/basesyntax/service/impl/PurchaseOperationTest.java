package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private final FruitOperation purchaseOperation = new PurchaseOperation();

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 150);
    }

    @Test
    public void applyPurchaseOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "banana", 100);
        int expected = 50;
        int actual = purchaseOperation.apply(fruitRecordDto);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyPurchaseOperationWithNotEnoughQuantityOnStorage_NotOk() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "banana", 200);
        purchaseOperation.apply(fruitRecordDto);
    }
}
