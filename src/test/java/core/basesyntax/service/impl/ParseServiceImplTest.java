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
    void parseFromString_emptyList_ok() {
        List<String> transactionLine = List.of();
        assertTrue(parseService.parseFromString(transactionLine).isEmpty());
    }

    @Test
    void parseFromString_validData_parsesCorrectNumberOfTransactions() {
        List<String> transactionLine = List.of("b,banana,10", "s,apple,5");
        List<FruitTransaction> result = parseService.parseFromString(transactionLine);
        assertEquals(2, result.size());
    }

    @Test
    void parseFromString_validData_allTransactionsHaveThreeFields() {
        List<String> transactionLine = List.of("b,banana,10", "s,apple,5");
        List<FruitTransaction> result = parseService.parseFromString(transactionLine);
        for (FruitTransaction transaction : result) {
            assertEquals(3, transaction.getClass().getDeclaredFields().length);
        }
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

    @Test
    void parseFromString_unrecognizedOperationType() {
        List<String> transactionLine = List.of("x,banana,10");
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> parseService.parseFromString(transactionLine));

        String expectedMessage = "Unknown operation type:" + "x";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
