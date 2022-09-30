package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private FruitDao fruitDao;
    private FruitTransaction transaction;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        transaction = new FruitTransaction();
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Test
    public void handle_ReturnOperation_Ok() {
        fruitDao.addFruit("kiwi", 500);
        fruitDao.addFruit("apple", 200);
        transaction.setFruit("kiwi");
        transaction.setQuantity(30);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        returnOperationHandler.handle(transaction);
        int actual = fruitDao.getQuantity("kiwi");
        int expected = 530;
        Assert.assertEquals(actual, expected);
    }
}
