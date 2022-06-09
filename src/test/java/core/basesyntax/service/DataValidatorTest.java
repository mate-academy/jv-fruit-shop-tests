package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.DataValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorTest {
    public static final String BANANA = "banana";
    private static DataValidator splitDataValidator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        splitDataValidator = new DataValidatorImpl();
    }

    @Test
    public void getTransactionFromRow_invalidData_notOk() {
        boolean expected = false;
        boolean actual = splitDataValidator
                .isValidDataFromCsv(new String[]{"b", BANANA, "n", "b", BANANA, "n" });
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isValidDataFromCsv(new String[]{"b", BANANA, "n"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isValidDataFromCsv(new String[]{"t", BANANA, "10"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isValidDataFromCsv(new String[]{"t", BANANA, "t"});
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionFromRow_validData_Ok() {
        boolean expected = true;
        boolean actual = splitDataValidator
                .isValidDataFromCsv(new String[]{"b", "banana", "10"});
        assertEquals(expected, actual);
    }
}
