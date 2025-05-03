package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static FruitDao fruitDao;
    private static SupplyOperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitDao);
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
    public void process_supplyFruits_Ok() {
        int bananaQuantity = 30;
        String fruitName = "banana";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                fruitName, bananaQuantity);
        operationHandler.process(transaction);
        int actualQuantity = fruitDao.get(fruitName);
        assertEquals(bananaQuantity, actualQuantity);

        bananaQuantity = 50;
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                fruitName, bananaQuantity);
        operationHandler.process(transaction);
        actualQuantity = fruitDao.get(fruitName);
        int expectedBananaQuantity = 80;
        assertEquals(expectedBananaQuantity, actualQuantity);
    }
}
