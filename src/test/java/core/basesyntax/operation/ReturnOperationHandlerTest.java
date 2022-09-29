package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitTransaction transaction = new FruitTransaction();
    private final ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler(fruitDao);

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
