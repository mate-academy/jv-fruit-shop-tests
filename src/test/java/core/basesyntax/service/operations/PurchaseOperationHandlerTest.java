package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static PurchaseOperationHandler operationHandler;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
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
    public void process_purchaseFruits_Ok() {
        int bananaQuantity = 100;
        int purchaseBananaQuantity = 99;
        String fruitName = "banana";
        fruitDao.add(fruitName, bananaQuantity);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                fruitName, purchaseBananaQuantity);
        operationHandler.process(transaction);
        int expectedBananaQuantity = 1;
        int actualBananaQuantity = fruitDao.get(fruitName);
        assertEquals(expectedBananaQuantity, actualBananaQuantity);
    }

    @Test
    public void process_notEnoughFruits_notOk() {
        int bananaQuantity = 100;
        int purchaseBananaQuantity = 120;
        String fruitName = "banana";
        fruitDao.add(fruitName, bananaQuantity);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                fruitName, purchaseBananaQuantity);
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Not enough "
                + transaction.getFruit() + " in shop.");
        operationHandler.process(transaction);
    }
}
