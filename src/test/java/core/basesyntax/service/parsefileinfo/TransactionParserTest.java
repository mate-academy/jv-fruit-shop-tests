package core.basesyntax.service.parsefileinfo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.exceptions.ValidationException;
import core.basesyntax.service.functionalityexpansion.ActivityType;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final String VALID_STRING = "b,banana,20";
    private final TransactionParser parser = new TransactionParser();

    @Test
    void parse_invalidLength_notOk() {
        String activityString = "INVALID_STRING";
        assertThrows(ValidationException.class,
                () -> parser.parse(activityString));
    }

    @Test
    void parse_validTypeReturn_Ok() {
        FruitTransactionInfo expected =
                new FruitTransactionInfo(ActivityType.BALANCE, "banana", 20);
        FruitTransactionInfo actual = parser.parse(VALID_STRING);

        assertEquals(expected, actual);
    }
}
