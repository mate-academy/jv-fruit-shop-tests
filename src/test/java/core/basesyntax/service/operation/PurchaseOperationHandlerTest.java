package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitOperationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @Test
    public void purchaseHandleOperation_Ok() {
        fruitDao.addFruit("apple",100);
        fruitDao.addFruit("banana",200);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(30);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitOperationHandler.handle(fruitTransaction);
        int actual = fruitDao.getQuantity("apple");
        int expected = 70;
        assertEquals(expected,actual);
    }
}
