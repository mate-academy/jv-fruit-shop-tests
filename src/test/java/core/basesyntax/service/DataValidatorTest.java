package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.DataValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorTest {
    private static final String BANANA = "banana";
    private static DataValidator splitDataValidator;

    @BeforeClass
    public static void beforeClass() {
        splitDataValidator = new DataValidatorImpl();
    }

    @Test
    public void getTransactionFromRow_invalidData_notOk() {
        assertFalse(splitDataValidator
                .isValidDataFromCsv(new String[]{"b", BANANA, "n", "b", BANANA, "n" }));
        assertFalse(splitDataValidator
                .isValidDataFromCsv(new String[]{"b", BANANA, "n"}));
        assertFalse(splitDataValidator
                .isValidDataFromCsv(new String[]{"t", BANANA, "10"}));
        assertFalse(splitDataValidator
                .isValidDataFromCsv(new String[]{"t", BANANA, "t"}));
    }

    @Test
    public void getTransactionFromRow_validData_ok() {
        assertTrue(splitDataValidator
                .isValidDataFromCsv(new String[]{"b", "banana", "10"}));
    }
}
