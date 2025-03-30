package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final int OPERATION_POSITION = 0;
    private Function<String, FruitTransaction> parser;

    @BeforeEach
    void setUp() {
        parser = new TransactionParser();
    }

    @Test
    void apply_validStringTransaction_ok() {
        String inputTransaction = "b,banana,80";
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 80);
        FruitTransaction actual = parser.apply(inputTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void apply_emptyTransaction_notOk() {
        String inputTransaction = "";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.apply(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }

    @Test
    void apply_invalidCodeTransaction_notOk() {
        String inputTransaction = "c,apple,60";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.apply(inputTransaction));
        assertEquals("Unknown operation code: "
                + inputTransaction.charAt(OPERATION_POSITION), exception.getMessage());
    }

    @Test
    void apply_invalidFormatTransaction_notOk() {
        String inputTransaction = "c.apple.60";
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.apply(inputTransaction));
        assertEquals("Invalid format input transaction"
                + inputTransaction, exception.getMessage());
    }
}
