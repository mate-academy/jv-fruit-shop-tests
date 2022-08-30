package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

public class SupplyOperationHandlerTest {

    @Test
    public void supplyOperationValid_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.addFruit("apple",100);
        fruitDao.addFruit("banana",200);
        final FruitOperationHandler fruitOperationHandler = new SupplyOperationHandler(fruitDao);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(30);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitOperationHandler.handle(fruitTransaction);
        int actual = fruitDao.getQuantity("apple");
        int expected = 130;
        assertEquals(expected,actual);
    }
}
