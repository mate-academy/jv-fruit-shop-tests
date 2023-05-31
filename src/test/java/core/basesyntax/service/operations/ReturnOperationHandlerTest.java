package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static FruitDao fruitDao;
    private static ReturnOperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Before
    public void setUp() throws Exception {
        fruitDao.clear();
    }

    @After
    public void tearDown() throws Exception {
        fruitDao.clear();
    }

    @Test
    public void process_returnFruits_Ok() {
        int bananaQuantity = 20;
        String fruitName = "banana";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruitName, bananaQuantity);
        operationHandler.process(transaction);
        int actualQuantity = fruitDao.get(fruitName);
        assertEquals(bananaQuantity, actualQuantity);

        bananaQuantity = 90;
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruitName, bananaQuantity);
        operationHandler.process(transaction);
        actualQuantity = fruitDao.get(fruitName);
        int expectedBananaQuantity = 110;
        assertEquals(expectedBananaQuantity, actualQuantity);
    }
}
