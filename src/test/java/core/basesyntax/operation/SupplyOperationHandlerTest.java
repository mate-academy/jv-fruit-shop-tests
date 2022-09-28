package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitTransaction transaction;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        transaction = new FruitTransaction();
        supplyOperationHandler = new SupplyOperationHandler(fruitDao);
    }

    @Test
    public void handle_HandleSupplyOperation_Ok() {
        fruitDao.addFruit("apple", 50);
        fruitDao.addFruit("kiwi", 200);
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyOperationHandler.handle(transaction);
        int expected = 100;
        int actual = fruitDao.getQuantity("apple");
        Assert.assertEquals(expected, actual);
    }
}
