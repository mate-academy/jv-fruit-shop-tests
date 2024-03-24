package core.basesyntax.service.parsefileinfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private String validString;
    private final TransactionParser parser = new TransactionParser();

    @AfterEach
    void tearDown() {
        validString = null;
    }

    @Test
    void parse_ValidStringFormat_Ok() {
        validString = "b,banana,20";
        assertTrue(isValidFormat(validString), "Valid string format should return true");
    }

    @Test
    void parse_InvalidStringFormat_Ok() {
        validString = "bd,banana,s";
        assertFalse(isValidFormat(validString), "Valid string format should return true");
    }

    @Test
    void parse_ValidTypeReturn_Ok() {
        validString = "b,banana,20";
        FruitTransactionInfo actualTransactionInfo = parser.parse(validString);

        assertSame(FruitTransactionInfo.class, actualTransactionInfo.getClass());
    }

    private boolean isValidFormat(String input) {
        String[] parts = input.split(",");
        if (parts.length != 3) {
            return false;
        }
        String type = parts[0];
        String quantity = parts[2];
        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            return false;
        }

        return type.length() == 1;
    }
}
