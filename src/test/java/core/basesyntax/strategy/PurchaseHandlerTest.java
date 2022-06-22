package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.db.Shop;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        ShopDao shopDao = new ShopDaoImpl();
        handler = new PurchaseHandler(shopDao);
    }

    @Test
    public void handle_purchaseOperation_Ok() {
        Shop.fruits.put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        handler.handle(fruitTransaction);
        int expected = 40;
        int actual = Shop.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nonExistFruit_notOk() {
        FruitTransaction fruitNonExistTransaction = new FruitTransaction();
        fruitNonExistTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitNonExistTransaction.setFruit("I am not apple or banana");
        fruitNonExistTransaction.setQuantity(10);
        handler.handle(fruitNonExistTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_overbuy_notOk() {
        Shop.fruits.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(11);
        handler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Shop.fruits.clear();
    }
}
