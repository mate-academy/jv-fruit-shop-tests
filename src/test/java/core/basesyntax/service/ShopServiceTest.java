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
    public void shopService_makeTransaction_emptyFruitTransaction_NotOk() {
        ShopService shopService = new ShopServiceImpl();
        assertThrows(RuntimeException.class,
                () -> shopService.makeTransaction(new FruitTransaction()));
    }

    @Test
    public void shopService_makeTransaction_nullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(null);
        ShopService shopService = new ShopServiceImpl();
        assertThrows(RuntimeException.class,
                () -> shopService.makeTransaction(fruitTransaction));
    }

    @Test
    public void shopService_makeTransaction_nullOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        ShopService shopService = new ShopServiceImpl();
        assertThrows(RuntimeException.class,
                () -> shopService.makeTransaction(fruitTransaction));
    }

    @Test
    public void shopService_makeTransaction_nullFruit_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(10);
        ShopService shopService = new ShopServiceImpl();
        assertThrows(RuntimeException.class,
                () -> shopService.makeTransaction(fruitTransaction));
    }
}
