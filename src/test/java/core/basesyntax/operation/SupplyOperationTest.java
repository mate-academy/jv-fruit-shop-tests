package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;
    private static final String FRUIT = "banana";
    private static final Integer FIRST_QUANTITY = 100;
    private static final Integer SECOND_QUANTITY = 30;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperation();
    }

    @After
    public void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void handleOperation_getSupply_Ok() {
        //arrange
        Storage.getFruitStorage().put(FRUIT, FIRST_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, SECOND_QUANTITY);
        Integer expected = FIRST_QUANTITY + SECOND_QUANTITY;

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = Storage.getFruitStorage().get(FRUIT);

        //assert
        assertEquals("SupplyOperation should update a quantity in DB.",
                expected, actual);
    }

    @Test
    public void handleOperation_addNewFruitInStorage_Ok() {
        //arrange
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT, SECOND_QUANTITY);
        Integer expected = SECOND_QUANTITY;

        //act
        operationHandler.handleOperation(transaction);
        Integer actual = Storage.getFruitStorage().get(FRUIT);

        //assert
        assertEquals("SupplyOperation should add new transaction in DB.",
                expected, actual);
    }
}
