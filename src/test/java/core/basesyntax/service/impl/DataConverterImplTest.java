package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
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
    void convert_ShouldThrowExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(null), "Input list can't be null");
    }

    @Test
    void convert_ShouldConvertValidLines() {
        List<String> lines = List.of(
                "b,banana,20",
                "s,apple,100",
                "p,orange,5"
        );

        List<FruitTransaction> transactions = dataConverter.convert(lines);

        assertEquals(3, transactions.size());
        assertEquals(new FruitTransaction(Operation.BALANCE, "banana", 20), transactions.get(0));
        assertEquals(new FruitTransaction(Operation.SUPPLY, "apple", 100), transactions.get(1));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "orange", 5), transactions.get(2));
    }

    @Test
    void convert_ShouldThrowExceptionForInvalidLineFormat() {
        List<String> lines = List.of(
                "b,banana,20",
                "invalid_line",
                "p,orange,5"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(lines), "Invalid line format: invalid_line");
    }

    @Test
    void convert_ShouldThrowExceptionForInvalidQuantityFormat() {
        List<String> lines = List.of(
                "b,banana,20",
                "s,apple,invalid_quantity",
                "p,orange,5"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(lines),
                "Invalid quantity format in line: s,apple,invalid_quantity");
    }

    @Test
    void convert_ShouldThrowExceptionForInvalidOperationCode() {
        List<String> lines = List.of(
                "b,banana,20",
                "x,apple,100",
                "p,orange,5"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(lines), "Invalid operation code in line: x,apple,100");
    }
}
