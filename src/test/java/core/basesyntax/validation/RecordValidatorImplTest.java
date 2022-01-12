package core.basesyntax.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordValidatorImplTest {
    private static RecordValidatorImpl recordValidator;
    private final String[] listRecordsTrue = {"b", "banana", "70"};
    private final String[] listRecordsFalseAmount = {"b", "banana", "70a"};
    private final String[] listRecordsFalseActivity = {"b1", "banana", "70"};
    private final String[] listRecordsFalseName = {"b", "banana7", "70"};

    @BeforeAll
    static void beforeAll() {
        recordValidator = new RecordValidatorImpl();
    }

    @Test
    void isValidListRecordOk() {
        boolean validInput = recordValidator.isValidInput(listRecordsTrue);
        assertTrue(validInput);
    }

    @Test
    void isValidListRecordAmountNotOk() {
        assertThrows(RuntimeException.class,
                () -> recordValidator.isValidInput(listRecordsFalseAmount));
    }

    @Test
    void isValidListRecordActivityNotOk() {
        assertThrows(RuntimeException.class,
                () -> recordValidator.isValidInput(listRecordsFalseActivity));
    }

    @Test
    void isValidListRecordNameNotOk() {
        assertThrows(RuntimeException.class,
                () -> recordValidator.isValidInput(listRecordsFalseName));
    }
}
