package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;
    private static final String FRUIT = "banana";
    private static final Integer FIRST_QUANTITY = 50;
    private static final Integer SECOND_QUANTITY = 150;
    private static Map<String, Integer> fruitStorage;
    private static MockedStatic<Storage> mockStorage;

    @BeforeClass
    public static void beforeClass() {
        mockStorage = mockStatic(Storage.class);
        fruitStorage = new HashMap<>();
    }

    @Before
    public void setUp() {
        operationHandler = new BalanceOperation();
    }

    @After
    public void storageClear() {
        fruitStorage.clear();
    }

    @AfterClass
    public static void closeMock() {
        mockStorage.close();
    }

    @Test
    public void handleOperation_getBalance_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, FIRST_QUANTITY);
        Integer expected = FIRST_QUANTITY;
        when(Storage.getFruitStorage()).thenReturn(fruitStorage);

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = fruitStorage.get(FRUIT);

        //assert
        assertEquals("BalanceOperation should add a pair of fruit-quantity to DB.",
                expected, actual);
    }

    @Test
    public void handleOperation_updateInStorage_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, SECOND_QUANTITY);
        fruitStorage.put(transaction.getFruit(), transaction.getQuantity());
        Integer expected = SECOND_QUANTITY;
        when(Storage.getFruitStorage()).thenReturn(fruitStorage);

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = fruitStorage.get(FRUIT);

        //assert
        assertEquals("BalanceOperation should update the quantity in DB.",
                expected, actual);
    }

    @Test
    public void handleOperation_addZeroQuantity_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, 0);
        Integer expected = 0;
        when(Storage.getFruitStorage()).thenReturn(fruitStorage);

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = fruitStorage.get(FRUIT);

        //assert
        assertEquals("BalanceOperation should add 0 quantity for fruit in DB.",
                expected, actual);
    }
}
