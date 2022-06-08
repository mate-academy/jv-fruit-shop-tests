package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.service.impl.DataValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataValidatorTest {
    private static DataValidator splitDataValidator;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        splitDataValidator = new DataValidatorImpl();
    }

    @Test
    public void getTransactionFromRow_invalidData_notOk() {
        boolean expected = true;
        boolean actual = splitDataValidator
                .isNotValidDataFromCsv(new String[]{"b", "banana", "n", "b", "banana", "n" });
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValidDataFromCsv(new String[]{"b", "banana", "n"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValidDataFromCsv(new String[]{"t", "banana", "10"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValidDataFromCsv(new String[]{"t", "banana", "t"});
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionFromRow_validData_Ok() {
        boolean expected = false;
        boolean actual = splitDataValidator
                .isNotValidDataFromCsv(new String[]{"b", "banana", "10"});
        assertEquals(expected, actual);
    }
}
