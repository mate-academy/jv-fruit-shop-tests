package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final String FRUIT = "apple";
    private static final int AMOUNT = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitDao);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT, AMOUNT);
    }

    @Test
    public void operationBalanceIs_Ok() {
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Optional.of(AMOUNT), fruitDao.get(FRUIT));
    }

    @After
    public void tearDown() {
        operationHandler = null;
    }
}
