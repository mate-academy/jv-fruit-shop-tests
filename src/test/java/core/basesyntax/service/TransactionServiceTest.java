package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.TransactionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
    private static TransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        transactionService = new TransactionServiceImpl();
    }

    @Test
    void parseTransactions_invalidOperationType_notOk() {
        List<String> data = List.of("b,banana,20", "b,apple,100", "s,banana,100", "k,apple,30");
        assertThrows(InvalidDataException.class, () -> transactionService.parseTransactions(data));
    }

    @Test
    void parseTransactions_negativeAmount_notOk() {
        List<String> data = List.of("b,banana,20", "b,apple,-10", "s,banana,100");
        assertThrows(InvalidDataException.class, () -> transactionService.parseTransactions(data));
    }

    @Test
    void parseTransactions_nonNumericAmount_notOk() {
        List<String> invalidCsvData = List.of("b,banana,20", "b,apple,1d0", "s,banana,100");
        assertThrows(InvalidDataException.class,
                () -> transactionService.parseTransactions(invalidCsvData)
        );
    }

    @Test
    void parseTransactions_invalidCsvFormat_notOk() {
        List<String> invalidCsvData = List.of("b,banana,20", "b,apple 10", "s,banana,100");
        assertThrows(InvalidDataException.class,
                () -> transactionService.parseTransactions(invalidCsvData)
        );
    }

    @Test
    void parseTransactions_valid_ok() {
        List<String> data = List.of("b,banana,20", "b,apple,10", "s,banana,100");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> parsedTransactions = transactionService.parseTransactions(data);
        assertEquals(expectedTransactions, parsedTransactions);
    }
}
