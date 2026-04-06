package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validLines_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,10"
        );
        List<FruitTransaction> result = dataConverter.convert(input);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(1).getFruit());
    }

    @Test
    void convert_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> dataConverter.convert(null));
    }

    @Test
    void convert_wrongFormat_notOk() {
        List<String> input = List.of("type,fruit,quantity", "p,banana,not_a_number");
        assertThrows(NumberFormatException.class, () -> dataConverter.convert(input));
    }

    @Test
    void convert_emptyFruitName_ok() {
        List<String> input = List.of("type,fruit,quantity", "b,,10");
        var result = dataConverter.convert(input);
        assertEquals("", result.get(0).getFruit());
    }

    @Test
    void convert_negativeQuantity_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,-5");
        assertThrows(RuntimeException.class, () -> dataConverter.convert(input));
    }

    @Test
    void convert_invalidLineFormat_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana");
        assertThrows(RuntimeException.class, () -> dataConverter.convert(input));
    }
}
