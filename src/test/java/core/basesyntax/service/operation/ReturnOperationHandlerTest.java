package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

public class ReturnOperationHandlerTest {

    @Test
    public void returnHandleOperationValid_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.addFruit("apple",100);
        fruitDao.addFruit("banana",200);
        final FruitOperationHandler fruitOperationHandler = new ReturnOperationHandler(fruitDao);
        final FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(30);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitOperationHandler.handle(fruitTransaction);
        int actual = fruitDao.getQuantity("apple");
        int expected = 130;
        assertEquals(expected,actual);
    }
}
