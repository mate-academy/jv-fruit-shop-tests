package core.basesyntax.service.parsefileinfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final int TYPE_INDEX = 0;
    private static final int EXPECTED_PARTS_COUNT = 3;
    private static final int QUANTITY_INDEX = 2;
    private static final int MINIMUM_TYPE_LENGTH = 1;
    private static final String VALID_STRING = "b,banana,20";
    private static final String INVALID_STRING = "b,banana,s";
    private static final String SEPARATOR = ",";
    private final TransactionParser parser = new TransactionParser();

    @Test
    void parse_validStringFormat_Ok() {
        assertTrue(isValidFormat(VALID_STRING), "Valid string format should return true");
    }

    @Test
    void parse_invalidStringFormat_Ok() {
        assertFalse(isValidFormat(INVALID_STRING), "Valid string format should return true");
    }

    @Test
    void parse_validTypeReturn_Ok() {
        FruitTransactionInfo actualTransactionInfo = parser.parse(VALID_STRING);

        assertSame(FruitTransactionInfo.class, actualTransactionInfo.getClass());
    }

    private boolean isValidFormat(String input) {
        String[] parts = input.split(SEPARATOR);
        if (parts.length != EXPECTED_PARTS_COUNT) {
            return false;
        }
        String type = parts[TYPE_INDEX];
        String quantity = parts[QUANTITY_INDEX];
        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            return false;
        }

        return type.length() == MINIMUM_TYPE_LENGTH;
    }
}
