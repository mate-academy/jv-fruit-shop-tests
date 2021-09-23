package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;
import java.util.Map;
import org.junit.Test;

public class SubstractionHendlerImplTest {
    private static final Map<String, Integer> FRUIT_COUNT = Storage.FRUIT_COUNT;
    private OperationHendler substraction = new SubstractionHendlerImpl();

    @Test
    public void apply_validInput_ok() {
        FRUIT_COUNT.put("apple", 100);
        substraction.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 50));
        int expected = 50;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }

    @Test
    public void apply_invalidInput_ok() {
        FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, "orange", 50)));
        FRUIT_COUNT.clear();
    }

    @Test
    public void apply_nullInput_Ok() {
        FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, null, 50)));
        FRUIT_COUNT.clear();
    }

    @Test
    public void apply_notValidResult_notOk() {
        FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 150)));
        FRUIT_COUNT.clear();
    }
}
