package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operationsservice.SupplyFruitOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyFruitOperationHandlerTest {
    private static SupplyFruitOperationHandler supplyFruitOperationHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        supplyFruitOperationHandler = new SupplyFruitOperationHandler(fruitDao);
    }

    @Test
    public void handle_supplyOperation_Ok() {
        FruitsStorage.fruits.put("apple", 90);
        supplyFruitOperationHandler.handle(getTransaction());
        Integer actualQuantity = FruitsStorage.fruits.get("apple");
        Integer expectedQuantity = 140;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    private FruitTransaction getTransaction() {
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(50);
        appleTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        return appleTransaction;
    }
}
