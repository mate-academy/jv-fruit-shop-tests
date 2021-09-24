package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;
import org.junit.AfterClass;
import org.junit.Test;

public class SubstractionHendlerImplTest {
    private final OperationHendler substraction = new SubstractionHendlerImpl();

    @Test
    public void apply_validInput_ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        substraction.apply(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 50));
        int expected = 50;
        int actual = Storage.FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @Test
    public void apply_invalidInput_ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, "orange", 50)));
    }

    @Test
    public void apply_nullInput_Ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, null, 50)));
    }

    @Test
    public void apply_notValidResult_notOk() {
        Storage.FRUIT_COUNT.put("apple", 100);
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> substraction.apply(
                        new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 150)));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.FRUIT_COUNT.clear();
    }
}
