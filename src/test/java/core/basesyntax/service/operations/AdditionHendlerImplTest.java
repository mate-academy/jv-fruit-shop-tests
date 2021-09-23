package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;
import java.util.Map;
import org.junit.Test;

public class AdditionHendlerImplTest {
    private static final Map<String, Integer> FRUIT_COUNT = Storage.FRUIT_COUNT;
    private final OperationHendler addition = new AdditionHendlerImpl();

    @Test
    public void apply_validInput_ok() {
        FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 50));
        int expected = 150;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }

    @Test
    public void apply_invalidInput_ok() {
        FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "orange", 50));
        int expected = 100;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }

    @Test
    public void apply_nullInput_Ok() {
        FRUIT_COUNT.put("apple", 100);
        addition.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, null, 50));
        int expected = 100;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }
}
