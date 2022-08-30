package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    @Test
    public void purchaseHandleOperationValid_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.addFruit("apple",100);
        fruitDao.addFruit("banana",200);
        final FruitOperationHandler fruitOperationHandler = new PurchaseOperationHandler(fruitDao);
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
