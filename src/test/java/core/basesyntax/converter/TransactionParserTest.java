package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final int OPERATION_POSITION = 0;
    private TransactionParser parser;

    @BeforeEach
    void setUp() {
        parser = new TransactionParser();
    }

    @Test
    void parseToTransaction_validStringTransaction_ok() {
        String inputTransaction = "b,banana,80";
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 80);
        FruitTransaction actual = parser.parseToTransaction(inputTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void parseToTransaction_emptyTransaction_notOk() {
        String inputTransaction = "";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }

    @Test
    void parseToTransaction_invalidCodeTransaction_notOk() {
        String inputTransaction = "c,apple,60";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Unknown operation code: "
                + inputTransaction.charAt(OPERATION_POSITION), exception.getMessage());
    }

    @Test
    void parseToTransaction_invalidFormatTransaction_notOk() {
        String inputTransaction = "c.apple.60";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }

    @Test
    void parseToTransaction_quantityIsSymbol_notOk() {
        String inputTransaction = "s,apple,!m";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }

    @Test
    void parseToTransaction_quantityIsNegative_notOk() {
        String inputTransaction = "s,apple,-100";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }

    @Test
    void parseToTransaction_quantityIsNotCorrectDecimalNumber_notOk() {
        String inputTransaction = "s,apple,001";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseToTransaction(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }
}
