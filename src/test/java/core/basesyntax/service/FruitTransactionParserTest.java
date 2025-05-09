package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private FruitTransactionParser parser;

    @BeforeEach
    void setUp() {
        parser = new FruitTransactionParser();
    }

    @Test
    void parse_InvalidDataFormat_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList("s,apple", "p,banana,5", "orange,20");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(input)
        );
        assertTrue(exception.getMessage().contains("Invalid data format"));
    }

    @Test
    void parse_InvalidQuantityFormat_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList("s,apple,ten", "p,banana,5");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(input)
        );
        assertTrue(exception.getMessage().contains("Invalid quantity format"));
    }

    @Test
    void parse_NegativeQuantity_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList("s,apple,-10", "p,banana,5");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(input)
        );
        assertTrue(exception.getMessage().contains("Quantity must be non-negative"));
    }

    @Test
    void parse_EmptyInput_ReturnsEmptyList() {
        List<FruitTransaction> result = parser.parse(Arrays.asList());
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_InvalidOperation_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList("x,apple,10");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(input)
        );
        assertFalse(exception.getMessage().contains("Invalid operation type"));
    }

    @Test
    void parse_MixedValidAndInvalidEntries_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList("s,apple,10", "s,banana,five");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(input)
        );
        assertTrue(exception.getMessage().contains("Invalid quantity format"));
    }

    @Test
    void parse_ValidData_ReturnsCorrectFruitTransactions() {
        List<String> input = Arrays.asList("s,apple,10", "s,banana,5");
        List<FruitTransaction> result = parser.parse(input);
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getFruit().equals("apple"));
        assertTrue(result.get(0).getQuantity() == 10);
        assertTrue(result.get(1).getFruit().equals("banana"));
        assertTrue(result.get(1).getQuantity() == 5);
    }
}
