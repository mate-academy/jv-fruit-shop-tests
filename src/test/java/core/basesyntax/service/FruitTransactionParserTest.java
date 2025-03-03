package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void parse_ValidData_ReturnsCorrectTransactions() {
        List<String> input = Arrays.asList(
                "s,apple,10",
                "p,banana,5",
                "r,orange,20"
        );

        List<FruitTransaction> result = parser.parse(input);

        assertEquals(3, result.size());

        assertEquals(FruitTransaction.OperationType.SUPPLY, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(FruitTransaction.OperationType.PURCHASE, result.get(1).getOperation());
        assertEquals("banana", result.get(1).getFruit());
        assertEquals(5, result.get(1).getQuantity());

        assertEquals(FruitTransaction.OperationType.RETURN, result.get(2).getOperation());
        assertEquals("orange", result.get(2).getFruit());
        assertEquals(20, result.get(2).getQuantity());
    }

    @Test
    void parse_InvalidDataFormat_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList(
                "s,apple",
                "p,banana,5",
                "orange,20"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));
        assertTrue(exception.getMessage().contains("Invalid data format"));
    }

    @Test
    void parse_InvalidQuantityFormat_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList(
                "s,apple,ten",
                "p,banana,5"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));
        assertTrue(exception.getMessage().contains("Invalid quantity format"));
    }

    @Test
    void parse_NegativeQuantity_ThrowsIllegalArgumentException() {
        List<String> input = Arrays.asList(
                "s,apple,-10",
                "p,banana,5"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));
        assertTrue(exception.getMessage().contains("Quantity must be non-negative"));
    }

    @Test
    void parse_EmptyInput_ReturnsEmptyList() {
        List<String> input = Arrays.asList();

        List<FruitTransaction> result = parser.parse(input);

        assertTrue(result.isEmpty());
    }
}
