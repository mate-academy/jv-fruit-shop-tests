package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Optional;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String FRUIT = "banana";
    private static final int AMOUNT = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseHandler(fruitDao);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT, AMOUNT);
    }

    @Test
    public void handle_Ok() {
        fruitDao.add(FRUIT, AMOUNT);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Optional.of(0), fruitDao.get(FRUIT));
    }
}
