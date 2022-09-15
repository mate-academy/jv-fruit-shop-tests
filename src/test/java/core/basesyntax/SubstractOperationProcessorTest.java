package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationProcessor;
import core.basesyntax.strategy.impl.SubstractOperationProcessor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubstractOperationProcessorTest {
    private static final String PURCHASE = "p";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static OperationProcessor purchaseOperationProcessor;
    private static Transaction.Operation purchaseOperation;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void before() {
        fruitDao = new FruitDaoImpl();
        purchaseOperation = Transaction.Operation.getByCode(PURCHASE);
        purchaseOperationProcessor = new SubstractOperationProcessor(fruitDao);
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.put(APPLE, 20);
        Storage.fruitStorage.put(BANANA, 30);
    }

    @Test
    public void process_PurchaseBasic_Ok() {
        final int expectedApple = 10;
        final int expectedBanana = 25;
        Transaction transactionApple = new Transaction(purchaseOperation, APPLE, 10);
        Transaction transactionBanana = new Transaction(purchaseOperation, BANANA, 5);
        purchaseOperationProcessor.process(transactionApple);
        purchaseOperationProcessor.process(transactionBanana);
        int actualApple = fruitDao.get(APPLE);
        int actualBanana = fruitDao.get(BANANA);
        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
    }

    @Test(expected = RuntimeException.class)
    public void process_PurchaseMoreThanAvailable_Ok() {
        final int expectedApple = 0;
        final int expectedBanana = 0;
        Transaction transactionApple = new Transaction(purchaseOperation, APPLE, 25);
        Transaction transactionBanana = new Transaction(purchaseOperation, BANANA, 5);
        purchaseOperationProcessor.process(transactionApple);
        purchaseOperationProcessor.process(transactionBanana);
        int actualApple = fruitDao.get(APPLE);
        int actualBanana = fruitDao.get(BANANA);
        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
    }
}
