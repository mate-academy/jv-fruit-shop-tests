package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private final ParserServiceImpl parserService = new ParserServiceImpl();

    @Test
    void validInput_Ok() {
        List<String> lines = List.of("b,apple,10", "s,banana,5");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 5)
        );

        List<FruitTransaction> actualTransactions = parserService.parseTransactions(lines);
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void insufficientFields_ThrowsException() {
        List<String> input = List.of("b,apple");

        Exception exception = assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(input));
        assertTrue(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    void invalidQuantity_ThrowsException() {
        List<String> input = List.of("b,apple,one");

        Exception exception = assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(input));
        assertTrue(exception.getMessage().contains("Error parsing transaction"));
    }

    @Test
    void invalidFormat_ThrowsException() {
        List<String> input = List.of("P,apple,17");

        Exception exception = assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(input));
        assertTrue(exception.getMessage().contains("Error parsing transaction"));
    }

    @Test
    void emptyList_NotOk() {
        List<String> input = List.of();
        List<FruitTransaction> transactions = parserService.parseTransactions(input);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void nullInput_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> parserService.parseTransactions(null));
        assertNotNull("Input list cannot be null", exception.getMessage());
    }
}
