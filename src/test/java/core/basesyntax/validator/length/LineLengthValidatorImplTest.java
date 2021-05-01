package core.basesyntax.validator.length;

import org.junit.BeforeClass;
import org.junit.Test;

public class LineLengthValidatorImplTest {
    private static LineLengthValidator lineLengthValidator;
    private static final int CORRECT_LENGTH = 3;
    private static final String[] lineOk = {"b", "banana", "100"};
    private static final String[] lineNotOk = {"banana", "100"};
    private static final int LINE_NUMBER = 1;

    @BeforeClass
    public static void setUp() {
        lineLengthValidator = new LineLengthValidatorImpl();
    }

    @Test(expected = LineHasNotThreeWordsException.class)
    public void isLengthCorrectExceptionTest_Ok() {
        lineLengthValidator.isLengthCorrect(lineNotOk, LINE_NUMBER);
    }

    @Test
    public void isLengthCorrectTest_Ok() {
        lineLengthValidator.isLengthCorrect(lineOk, LINE_NUMBER);
    }
}