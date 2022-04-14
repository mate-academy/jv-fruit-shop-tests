package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationTest {
    private final TypeHandler purchaseOperation = new PurchaseOperation();

    @Test
    public void changeCountFruitAfterPurchaseOperation() {
        FruitDto fruitDto = new FruitDto("p", "banana", 10);
        Storage.fruits.put(fruitDto.getFruit(), 100);
        Integer expected = Storage.fruits.get(fruitDto.getFruit()) - 10;
        purchaseOperation.handle(fruitDto);
        Integer actual = Storage.fruits.get(fruitDto.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
