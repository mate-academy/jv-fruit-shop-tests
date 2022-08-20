package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operationsservice.ReturnFruitOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnFruitOperationHandlerTest {
    private static ReturnFruitOperationHandler returnFruitOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        returnFruitOperationHandler = new ReturnFruitOperationHandler(fruitDao);
    }

    @Test
    public void handle_returnOperation_Ok() {
        FruitsStorage.fruits.put("apple", 90);
        returnFruitOperationHandler.handle(getTransaction());
        Integer actualQuantity = FruitsStorage.fruits.get("apple");
        Integer expectedQuantity = 140;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    private FruitTransaction getTransaction() {
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(50);
        appleTransaction.setOperation(FruitTransaction.Operation.RETURN);
        return appleTransaction;
    }
}
