package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.impl.DataConverterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "operation,fruit,quantity",
                "b,apple,10",
                "p,banana,5"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_invalidRowFormat_notOk() {
        List<String> input = List.of(
                "operation,fruit,quantity",
                "b,apple"
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));
        assertTrue(exception.getMessage().contains("Invalid CSV row format"));
    }

    @Test
    void convertToTransaction_invalidNumberFormat_notOk() {
        List<String> input = List.of(
                "operation,fruit,quantity",
                "b,apple,ten"
        );
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));
        assertTrue(exception.getMessage().contains("Invalid number format in CSV"));
    }

    @Test
    void convertToTransaction_emptyList_ok() {
        List<FruitTransaction> result = dataConverter
                .convertToTransaction(List.of("operation,fruit,quantity"));
        assertTrue(result.isEmpty());
    }
}
