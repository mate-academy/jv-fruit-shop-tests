package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.service.impl.SplitDataValidatorCsvImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SplitDataValidatorTest {
    private static SplitDataValidator splitDataValidator;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        splitDataValidator = new SplitDataValidatorCsvImpl();
    }

    @Test
    public void getTransactionFromRow_invalidData_notOk() {
        boolean expected = true;
        boolean actual = splitDataValidator
                .isNotValid(new String[]{"b", "banana", "n", "b", "banana", "n" });
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValid(new String[]{"b", "banana", "n"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValid(new String[]{"t", "banana", "10"});
        assertEquals(expected, actual);
        actual = splitDataValidator
                .isNotValid(new String[]{"t", "banana", "t"});
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionFromRow_validData_Ok() {
        boolean expected = false;
        boolean actual = splitDataValidator.isNotValid(new String[]{"b", "banana", "10"});
        assertEquals(expected, actual);
    }
}
