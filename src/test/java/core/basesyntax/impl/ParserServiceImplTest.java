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
        List<FruitTransaction> fruitTransactions = parserService.parseTransactions(lines);

        assertEquals(2, fruitTransactions.size());
        assertEquals(Operation.BALANCE, fruitTransactions.get(0).getOperation());
        assertEquals("apple", fruitTransactions.get(0).getFruit());
        assertEquals(10, fruitTransactions.get(0).getQuantity());

        assertEquals(Operation.SUPPLY, fruitTransactions.get(1).getOperation());
        assertEquals("banana", fruitTransactions.get(1).getFruit());
        assertEquals(5, fruitTransactions.get(1).getQuantity());
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
        Exception exception = assertThrows(NullPointerException.class,
                () -> parserService.parseTransactions(null));
        assertNotNull(exception);
    }
}
