package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.db.Shop;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        ShopDao shopDao = new ShopDaoImpl();
        handler = new BalanceHandler(shopDao);
    }

    @Test
    public void handle_balanceOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(15);
        handler.handle(fruitTransaction);
        int expected = 15;
        int actual = Shop.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Shop.fruits.clear();
    }
}
