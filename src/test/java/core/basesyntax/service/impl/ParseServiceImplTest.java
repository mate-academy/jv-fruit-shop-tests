package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private ParseServiceImpl parseService;

    @BeforeEach
    void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseFromString_emptyList() {
        List<String> transactionLine = List.of();
        assertTrue(parseService.parseFromString(transactionLine).isEmpty());
    }

    @Test
    void parseFromString_validData() {
        List<String> transactionLine = List.of("b,banana,10", "s,apple,5");
        List<FruitTransaction> result =
                parseService.parseFromString(transactionLine);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                result.get(0).getType());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(3, result.get(0).getClass().getDeclaredFields().length);
    }

    @Test
    void parseFromString_invalidData() {
        List<String> transactionLine = List.of("b, banana");
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> parseService.parseFromString(transactionLine));

        String expectedMessage =
                "Invalid transaction format: " + transactionLine;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
