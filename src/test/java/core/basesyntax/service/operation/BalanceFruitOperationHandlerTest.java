package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operationsservice.BalanceFruitOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceFruitOperationHandlerTest {
    private static BalanceFruitOperationHandler balanceFruitOperationHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        balanceFruitOperationHandler = new BalanceFruitOperationHandler(fruitDao);
    }

    @Test
    public void handle_balanceOperation_Ok() {
        balanceFruitOperationHandler.handle(getTransaction());
        Integer actualQuantity = FruitsStorage.fruits.get("apple");
        Integer expectedQuantity = 120;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    private FruitTransaction getTransaction() {
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(120);
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        return appleTransaction;
    }
}
