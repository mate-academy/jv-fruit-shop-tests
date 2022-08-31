package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitOperationHandler fruitOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitDao = new FruitDaoImpl();
        fruitOperationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Test
    public void returnHandleOperation_Ok() {
        fruitDao.addFruit("apple",100);
        fruitDao.addFruit("banana",200);
        fruitTransaction.setQuantity(30);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitOperationHandler.handle(fruitTransaction);
        int actual = fruitDao.getQuantity("apple");
        int expected = 130;
        assertEquals(expected,actual);
    }
}
