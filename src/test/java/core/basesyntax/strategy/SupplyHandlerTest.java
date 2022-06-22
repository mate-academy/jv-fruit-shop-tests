package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.db.Shop;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        ShopDao shopDao = new ShopDaoImpl();
        handler = new ReturnHandler(shopDao);
    }

    @Test
    public void handle_supplyOperation_Ok() {
        Shop.fruits.put("banana", 5);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(5);
        handler.handle(fruitTransaction);
        int expected = 10;
        int actual = Shop.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Shop.fruits.clear();
    }
}
