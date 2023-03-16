package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class ReturnHandlerTest {
    private static TransactionHandler returnHandler;
    private static FruitTransaction returnTransaction;

    @Before
    public void setUp() {
        returnHandler = new ReturnHandler();

        returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("banana");
        returnTransaction.setQuantity(10);

        Storage.fruits.put("banana", 10);
    }

    @Test
    public void handle_ReturnTransaction_Ok() {
        Integer expected = 20;
        returnHandler.handle(returnTransaction);
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
