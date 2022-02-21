package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int TEST_FRUIT_AMOUNT_SMALLER = 10;
    private static final int TEST_FRUIT_AMOUNT_INVALID = -10;
    private static final int TEST_FRUIT_AMOUNT_BIGGER = 20;
    private static final int EXPECTED_AMOUNT = 10;
    private static final String TEST_FRUIT_TYPE = "avocado";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransactionTest;
    private static FruitDao fruitDao;
    private Fruit testFruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
        fruitTransactionTest = new FruitTransaction();
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() {
        fruitTransactionTest.setFruitType(TEST_FRUIT_TYPE);
        fruitTransactionTest.setAmount(TEST_FRUIT_AMOUNT_SMALLER);
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void changeData_transactionNull_notOk() {
        operationHandler.changeData(null);
    }

    @Test(expected = RuntimeException.class)
    public void changeData_fruitTypeNull_notOk() {
        fruitTransactionTest.setFruitType(null);
        operationHandler.changeData(fruitTransactionTest);
    }

    @Test(expected = RuntimeException.class)
    public void changeData_negativeFruitAmount_notOk() {
        fruitTransactionTest.setAmount(TEST_FRUIT_AMOUNT_INVALID);
        operationHandler.changeData(fruitTransactionTest);
    }

    @Test(expected = RuntimeException.class)
    public void changeData_negativeAmountInStorage_notOk() {
        testFruit = new Fruit();
        testFruit.setFruitType(TEST_FRUIT_TYPE);
        testFruit.setAmount(TEST_FRUIT_AMOUNT_SMALLER);
        Storage.fruits.add(testFruit);
        fruitTransactionTest.setAmount(TEST_FRUIT_AMOUNT_BIGGER);
        operationHandler.changeData(fruitTransactionTest);
    }

    @Test
    public void changeData_validTransaction_ok() {
        testFruit = new Fruit();
        testFruit.setFruitType(TEST_FRUIT_TYPE);
        testFruit.setAmount(TEST_FRUIT_AMOUNT_BIGGER);
        Storage.fruits.add(testFruit);
        operationHandler.changeData(fruitTransactionTest);
        Fruit actualFruit = fruitDao.get(fruitTransactionTest.getFruitType());
        assertEquals(EXPECTED_AMOUNT, actualFruit.getAmount());
    }
}
