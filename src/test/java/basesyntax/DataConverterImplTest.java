package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void convert_shouldConvertCorrectly() {
        List<String> input = List.of("b,banana,20");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convert_shouldSkipBlankLines() {
        List<String> input = new ArrayList<>();
        input.add("b,banana,10");
        input.add("");
        input.add("   ");
        input.add(null);
        input.add("s,apple,5");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(2, result.size());
    }

    @Test
    void convert_shouldThrowException_whenInputIsNull() {
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(null));
    }

    @Test
    void convert_shouldThrowException_whenInvalidFormat() {
        List<String> input = List.of("invalid_line");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convert_shouldThrowException_whenWrongDelimiter() {
        List<String> input = List.of("b-banana-10");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convert_shouldThrowException_whenUnknownOperation() {
        List<String> input = List.of("x,banana,10");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convert_shouldThrowException_whenQuantityIsNotNumber() {
        List<String> input = List.of("b,banana,abc");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }
}
