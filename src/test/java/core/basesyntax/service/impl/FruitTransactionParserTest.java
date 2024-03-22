package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.impl.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final String VALID_ROW = "b,apple,100";
    private static final FruitTransaction TRANSACTION_FROM_VALID_ROW
            = new FruitTransaction(OperationType.BALANCE, "apple", 100);
    private static final String ROW_WITH_INVALID_OPERATION_TYPE = "a,apple,100";
    private static final String ROW_WITH_NEGATIVE_OPERATION_VALUE = "a,apple,-100";
    private static final String ROW_WITH_INCORRECT_STRUCTURE = "apple,100,b";
    private static final String ROW_WITHOUT_SEPARATION = "bapple100";
    private TransactionParser<FruitTransaction> parser;

    @BeforeEach
    public void initParser() {
        parser = new FruitTransactionParser();
    }

    @Test
    public void parseValidRow_ok() {
        assertEquals(parser.parse(VALID_ROW), TRANSACTION_FROM_VALID_ROW);
    }

    @Test
    public void parseNullString_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(null));
        assertEquals("Error: This row is null!", exception.getMessage());
    }

    @Test
    public void parseRowWithInvalidOperationType_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(ROW_WITH_INVALID_OPERATION_TYPE));
        assertEquals("Invalid data in row: " + ROW_WITH_INVALID_OPERATION_TYPE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithNegativeOperationValue_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(ROW_WITH_NEGATIVE_OPERATION_VALUE));
        assertEquals("Invalid data in row: " + ROW_WITH_NEGATIVE_OPERATION_VALUE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithIncorrectStructure_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(ROW_WITH_INCORRECT_STRUCTURE));
        assertEquals("Invalid data in row: " + ROW_WITH_INCORRECT_STRUCTURE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseEmptyRow_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(""));
        assertEquals("Invalid data in row: "
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithoutSeparation_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> parser.parse(ROW_WITHOUT_SEPARATION));
        assertEquals("Invalid data in row: " + ROW_WITHOUT_SEPARATION
                + " The expected format should be: b,apple,100", exception.getMessage());
    }
}
