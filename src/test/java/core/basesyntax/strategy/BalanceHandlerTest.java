package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Optional;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String FRUIT = "banana";
    private static final int AMOUNT = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceHandler(fruitDao);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, AMOUNT);
    }

    @Test
    public void operationBalanceIs_Ok() {
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals("Amount of fruit: ", Optional.of(AMOUNT), fruitDao.get(FRUIT));
    }
}
