package core.basesyntax.service;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileValidatorTest {
    private static final int SHORT_ARRAY_LENGTH = 2;
    private static final int LONG_ARRAY_LENGTH = 4;
    private static FileValidator validator;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        validator = new FileValidatorImpl();
    }

    @Test
    public void validate_DataLengthGraterThree() {
        String[] data = new String[LONG_ARRAY_LENGTH];
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Incorrect quantity input data in this line: ");
        validator.validate(data);
    }

    @Test
    public void validate_DataLengthLessThree() {
        String[] data = new String[SHORT_ARRAY_LENGTH];
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Incorrect quantity input data in this line: ");
        validator.validate(data);
    }

    @Test
    public void validate_NegativeNumber() {
        String[] data = new String[]{"b", "banana", "-10"};
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Impossible input for quantity data: ");
        validator.validate(data);
    }
}
