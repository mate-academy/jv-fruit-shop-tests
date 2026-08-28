package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of("type,fruit,quantity",
                "b,banana,20");
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(1, actual.size());

        FruitTransaction transaction = actual.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE,
                transaction.getOperation()
        );
        assertEquals("banana", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_nullInput_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(null)
        );
    }

    @Test
    void convertToTransition_emptyInput_notOk() {
        List<String> input = List.of();

        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(input)
        );
    }

    @Test
    void convertToTransaction_invalidParts_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana"
        );
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,hello"
        );
        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(input)
        );
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,-20"
        );

        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(input)
        );
    }

    @Test
    void convertToTransaction_invalidOperation_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,20"
        );

        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(input)
        );
    }

}
