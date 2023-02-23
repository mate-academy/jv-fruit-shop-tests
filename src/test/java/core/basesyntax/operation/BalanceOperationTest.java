package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;
    private static final String FRUIT = "banana";
    private static final Integer FIRST_QUANTITY = 50;
    private static final Integer SECOND_QUANTITY = 150;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperation();
    }

    @After
    public void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void handleOperation_addToStorage_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, FIRST_QUANTITY);
        Integer expected = FIRST_QUANTITY;

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = Storage.getFruitStorage().get(FRUIT);

        //assert
        assertEquals("BalanceOperation should add a pair of fruit-quantity to DB.",
                expected, actual);
    }

    @Test
    public void handleOperation_updateInStorage_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, SECOND_QUANTITY);
        Storage.getFruitStorage().put(transaction.getFruit(),
                transaction.getQuantity());
        Integer expected = SECOND_QUANTITY;

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = Storage.getFruitStorage().get(FRUIT);

        //assert
        assertEquals("BalanceOperation should update the quantity in DB.",
                expected, actual);
    }

    @Test
    public void handleOperation_addZeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, 0);
        Integer expected = 0;

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = Storage.getFruitStorage().get(FRUIT);

        //assert
        assertEquals("BalanceOperation should add 0 quantity for fruit in DB.",
                expected, actual);
    }
}
