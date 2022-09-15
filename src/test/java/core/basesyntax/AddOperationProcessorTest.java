package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationProcessor;
import core.basesyntax.strategy.impl.AddOperationProcessor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationProcessorTest {
    private static final String SUPPLY = "s";
    private static final String RETURN = "r";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static Transaction.Operation supplyOperation;
    private static Transaction.Operation returnOperation;
    private static OperationProcessor addOperationProcessor;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void before() {
        fruitDao = new FruitDaoImpl();
        supplyOperation = Transaction.Operation.getByCode(SUPPLY);
        returnOperation = Transaction.Operation.getByCode(RETURN);
        addOperationProcessor = new AddOperationProcessor(fruitDao);
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put(APPLE, 20);
        Storage.fruitStorage.put(BANANA, 10);
    }

    @Test
    public void process_SupplyOperationSingle_Ok() {
        int expected = 30;
        Transaction transaction = new Transaction(supplyOperation, APPLE, 10);
        addOperationProcessor.process(transaction);
        int actual = fruitDao.get(APPLE);
        assertEquals(expected, actual);
    }

    @Test
    public void process_SupplyOperationMultiple_Ok() {
        final int expectedApple = 80;
        final int expectedBanana = 40;
        Transaction transactionApple = new Transaction(supplyOperation, APPLE, 30);
        Transaction transactionBanana = new Transaction(supplyOperation, BANANA, 30);
        addOperationProcessor.process(transactionApple);
        addOperationProcessor.process(transactionApple);
        addOperationProcessor.process(transactionBanana);
        assertEquals(expectedApple, fruitDao.get(APPLE).intValue());
        assertEquals(expectedBanana, fruitDao.get(BANANA).intValue());
    }

    @Test
    public void process_ReturnOperationSingle_Ok() {
        int expected = 50;
        Transaction transaction = new Transaction(returnOperation, APPLE, 30);
        addOperationProcessor.process(transaction);
        int actual = fruitDao.get(APPLE);
        assertEquals(expected, actual);
    }

    @Test
    public void process_ReturnOperationMultiple_Ok() {
        final int expectedApple = 80;
        final int expectedBanana = 40;
        Transaction transactionApple = new Transaction(returnOperation, APPLE, 30);
        Transaction transactionBanana = new Transaction(returnOperation, BANANA, 30);
        addOperationProcessor.process(transactionApple);
        addOperationProcessor.process(transactionApple);
        addOperationProcessor.process(transactionBanana);
        assertEquals(expectedApple, fruitDao.get(APPLE).intValue());
        assertEquals(expectedBanana, fruitDao.get(BANANA).intValue());
    }

    @Test
    public void process_ReturnAndSupplyOperations_Ok() {
        final int expectedApple = 60;
        final int expectedBanana = 30;
        Transaction transactionReturnApple = new Transaction(returnOperation, APPLE, 10);
        Transaction transactionReturnBanana = new Transaction(returnOperation, BANANA, 10);
        Transaction transactionSupplyApple = new Transaction(supplyOperation, APPLE, 10);
        Transaction transactionSupplyBanana = new Transaction(supplyOperation, BANANA, 10);
        addOperationProcessor.process(transactionReturnApple);
        addOperationProcessor.process(transactionReturnApple);
        addOperationProcessor.process(transactionReturnBanana);
        addOperationProcessor.process(transactionSupplyApple);
        addOperationProcessor.process(transactionSupplyApple);
        addOperationProcessor.process(transactionSupplyBanana);
        assertEquals(expectedApple, fruitDao.get(APPLE).intValue());
        assertEquals(expectedBanana, fruitDao.get(BANANA).intValue());
    }
}
