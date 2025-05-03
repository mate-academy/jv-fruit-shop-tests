package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FormaterService;
import core.basesyntax.service.impl.FormaterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FormaterServiceTest {
    private static FormaterService formaterService;

    @BeforeAll
    static void beforeAll() {
        formaterService = new FormaterServiceImpl();
    }

    @Test
    void form_validData_ok() {
        List<String> validData = List.of("b,banana,20", "b,apple,10", "s,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> actual = formaterService.form(validData);
        assertEquals(expected, actual);
    }

    @Test
    void form_invalidOperationType_notOk() {
        List<String> data = List.of("b,banana,20", "b,apple,100", "s,banana,100", "k,banana,1000");
        assertThrows(InvalidDataException.class, () -> formaterService.form(data));
    }

    @Test
    void form_negativeAmount_notOk() {
        List<String> invalidQuantityData = List.of("b,banana,20", "b,apple,-10", "s,banana,100");
        assertThrows(InvalidDataException.class, () -> formaterService.form(invalidQuantityData));
    }

    @Test
    void form_nonNumericAmount_notOk() {
        List<String> invalidCsvData = List.of("b,banana,20", "b,apple,1d0", "s,banana,100");
        assertThrows(InvalidDataException.class,
                () -> formaterService.form(invalidCsvData)
        );
    }

    @Test
    void form_invalidCsvFormat_notOk() {
        List<String> invalidCsvData = List.of("b,banana,20", "b,apple 10", "s,banana,100");
        assertThrows(InvalidDataException.class,
                () -> formaterService.form(invalidCsvData)
        );
    }
}
