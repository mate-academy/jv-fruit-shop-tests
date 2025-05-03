package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    public static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_validData_ok() {
        List<String> list = List.of("b,banana,25");

        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 25);

        List<FruitTransaction> actual = dataConverter.convertToTransaction(list);

        assertEquals(expected, actual.get(0));
    }

    @Test
    public void convertToTransaction_validData_notOk() {
        List<String> list = List.of("b,banana");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(list);
        });
    }

    @Test
    public void convertToTransaction_quantityIsANumber_notOk() {
        List<String> list = List.of("b,banana,abc");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(list);
        });
    }

    @Test
    public void convertToTransaction_quantityIsGreaterThanZero_notOk() {
        List<String> list = List.of("b,banana,-25");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(list);
        });
    }
}
