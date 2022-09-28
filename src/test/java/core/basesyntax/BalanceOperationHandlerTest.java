package core.basesyntax;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.operation.BalanceOperationHandler;

import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitTransaction transaction;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        transaction = new FruitTransaction();
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void handle_HandleBalanceOperation_Ok() {
        fruitDao.addFruit("banana", 20);
        fruitDao.addFruit("apple",50);
        transaction.setFruit("kiwi");
        transaction.setQuantity(5);
        balanceOperationHandler.handle(transaction);
        Map<String, Integer> allFruits = fruitDao.getAll();
        int expected = 3;
        int actual = allFruits.size();
        Assert.assertTrue(allFruits.containsKey("banana"));
        Assert.assertTrue(allFruits.containsValue(20));
        Assert.assertTrue(allFruits.containsKey("apple"));
        Assert.assertTrue(allFruits.containsValue(50));
        Assert.assertTrue(allFruits.containsKey("kiwi"));
        Assert.assertTrue(allFruits.containsValue(5));
        Assert.assertEquals(actual, expected);
    }
}
