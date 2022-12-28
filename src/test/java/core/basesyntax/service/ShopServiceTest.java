package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import org.junit.After;
import org.junit.Test;

public class ShopServiceTest {

    private FruitTransaction getDefaultFruitTransaction() {
        FruitTransaction firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstFruitTransaction.setFruit("banana");
        firstFruitTransaction.setQuantity(20);
        return firstFruitTransaction;
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void shopService_makeTransaction_Ok() {
        ShopService shopService = new ShopServiceImpl();
        shopService.makeTransaction(getDefaultFruitTransaction());
        Integer expectedQuantity = getDefaultFruitTransaction().getQuantity();
        Integer actualQuantity = Storage.fruits.get(getDefaultFruitTransaction().getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void shopService_makeTransaction_emptyValue_NotOk() {
        ShopService shopService = new ShopServiceImpl();
        assertThrows(NullPointerException.class,
                () -> shopService.makeTransaction(new FruitTransaction()));
    }
}
