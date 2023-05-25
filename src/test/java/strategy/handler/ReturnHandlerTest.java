package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class ReturnHandlerTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer QUANTITY_OF_FRUITS_IN_STORAGE = 10;
    private static final Integer QUANTITY_OF_FRUITS_TO_RETURN = 5;
    private static TransactionHandler returnHandler;
    private static FruitTransaction returnTransaction;

    @Before
    public void setUp() {
        returnHandler = new ReturnHandler();

        returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit(FRUIT_FOR_TEST);
        returnTransaction.setQuantity(QUANTITY_OF_FRUITS_TO_RETURN);

        Storage.fruits.put(FRUIT_FOR_TEST, QUANTITY_OF_FRUITS_IN_STORAGE);
    }

    @Test
    public void handle_ReturnTransaction_Ok() {
        Integer expected = 15;
        returnHandler.handle(returnTransaction);
        Integer actual = Storage.fruits.get(FRUIT_FOR_TEST);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
