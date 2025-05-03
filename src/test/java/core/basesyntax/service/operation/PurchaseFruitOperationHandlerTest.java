package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operationsservice.PurchaseFruitOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseFruitOperationHandlerTest {
    private static PurchaseFruitOperationHandler purchaseFruitOperationHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        purchaseFruitOperationHandler = new PurchaseFruitOperationHandler(fruitDao);
    }

    @Test
    public void handle_purchaseOperation_Ok() {
        FruitsStorage.fruits.put("apple", 90);
        purchaseFruitOperationHandler.handle(getTransaction());
        Integer actualQuantity = FruitsStorage.fruits.get("apple");
        Integer expectedQuantity = 40;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    private FruitTransaction getTransaction() {
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(50);
        appleTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        return appleTransaction;
    }
}
