package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.impl.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final TransactionParser<FruitTransaction> PARSER = new FruitTransactionParser();
    private static final String VALID_ROW = "b,apple,100";
    private static final FruitTransaction TRANSACTION_FROM_VALID_ROW
            = new FruitTransaction(OperationType.BALANCE, "apple", 100);
    private static final String ROW_WITH_INVALID_OPERATION_TYPE = "a,apple,100";
    private static final String ROW_WITH_NEGATIVE_OPERATION_VALUE = "a,apple,-100";
    private static final String ROW_WITH_INCORRECT_STRUCTURE = "apple,100,b";
    private static final String ROW_WITHOUT_SEPARATION = "bapple100";

    @Test
    public void parseValidRow_ok() {
        Assertions.assertEquals(PARSER.parse(VALID_ROW), TRANSACTION_FROM_VALID_ROW);
    }

    @Test
    public void parseNullString_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(null));
        Assertions.assertEquals("Error: This row is null!", exception.getMessage());
    }

    @Test
    public void parseRowWithInvalidOperationType_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(ROW_WITH_INVALID_OPERATION_TYPE));
        Assertions.assertEquals("Invalid data in row: " + ROW_WITH_INVALID_OPERATION_TYPE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithNegativeOperationValue_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(ROW_WITH_NEGATIVE_OPERATION_VALUE));
        Assertions.assertEquals("Invalid data in row: " + ROW_WITH_NEGATIVE_OPERATION_VALUE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithIncorrectStructure_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(ROW_WITH_INCORRECT_STRUCTURE));
        Assertions.assertEquals("Invalid data in row: " + ROW_WITH_INCORRECT_STRUCTURE
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseEmptyRow_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(""));
        Assertions.assertEquals("Invalid data in row: "
                + " The expected format should be: b,apple,100", exception.getMessage());
    }

    @Test
    public void parseRowWithoutSeparation_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> PARSER.parse(ROW_WITHOUT_SEPARATION));
        Assertions.assertEquals("Invalid data in row: " + ROW_WITHOUT_SEPARATION
                + " The expected format should be: b,apple,100", exception.getMessage());
    }
}
