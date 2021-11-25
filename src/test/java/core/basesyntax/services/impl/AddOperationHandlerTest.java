package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.TransactionDto;
import core.basesyntax.services.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler addOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        addOperationHandler = new AddOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_firstTimeAdd_Ok() {
        String fruitName = "apple";
        int expectedValue = 100;
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, expectedValue);
        addOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                expectedValue, actual);
    }

    @Test
    public void apply_addToExist_Ok() {
        String fruitName = "apple";
        int currentValue = 100;
        Storage.storage.put(fruitName, currentValue);
        int valueToAdd = 50;
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, valueToAdd);
        addOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                currentValue + valueToAdd, actual);
    }

    @Test
    public void apply_addZeroFirstTime_Ok() {
        String fruitName = "apple";
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, 0);
        addOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                0, actual);
    }

    @Test
    public void apply_addZeroToExist_Ok() {
        String fruitName = "apple";
        int currentValue = 100;
        Storage.storage.put(fruitName, currentValue);
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, 0);
        addOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                currentValue, actual);
    }

    @Test
    public void apply_addIntMaxValue_Ok() {
        String fruitName = "apple";
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, Integer.MAX_VALUE);
        addOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                Integer.MAX_VALUE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullValue_NotOk() {
        addOperationHandler.apply(null);
    }
}
