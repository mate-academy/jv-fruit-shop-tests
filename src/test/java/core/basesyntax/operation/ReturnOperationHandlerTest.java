package core.basesyntax.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitTransaction transaction;
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        transaction = new FruitTransaction();
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Test
    public void handle_HandleReturnOperation_Ok() {
        fruitDao.addFruit("blueberries", 500);
        fruitDao.addFruit("strawberries", 200);
        transaction.setFruit("blueberries");
        transaction.setQuantity(30);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        returnOperationHandler.handle(transaction);
        int actual = fruitDao.getQuantity("blueberries");
        int expected = 530;
        Assert.assertEquals(actual, expected);
    }
}
