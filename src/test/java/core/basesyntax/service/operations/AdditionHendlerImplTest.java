package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;
import org.junit.AfterClass;
import org.junit.Test;

public class AdditionHendlerImplTest {
    private final OperationHendler addition = new AdditionHendlerImpl();

    @Test
    public void apply_validInput_ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 50));
        int expected = 150;
        int actual = Storage.FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @Test
    public void apply_invalidInput_ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "orange", 50));
        int expected = 100;
        int actual = Storage.FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @Test
    public void apply_nullInput_Ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, null, 50));
        int expected = 100;
        int actual = Storage.FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.FRUIT_COUNT.clear();
    }
}
