package core.basesyntax.validator.type;

import org.junit.BeforeClass;
import org.junit.Test;

public class TypeValidatorImplTest {
    private static final String TYPE_OK = "b";
    private static final String TYPE_NOT_OK = "k";
    private static final int LINE_NUMBER = 1;
    private static TypeValidator typeValidator;

    @BeforeClass
    public static void setUp() {
        typeValidator = new TypeValidatorImpl();
    }

    @Test
    public void isTypeCorrectTest_Ok() {
        typeValidator.isTypeCorrect(TYPE_OK, LINE_NUMBER);
    }

    @Test(expected = UnavaliableTypeException.class)
    public void isTypeCorrectExceptionTest_Ok() {
        typeValidator.isTypeCorrect(TYPE_NOT_OK, LINE_NUMBER);
    }
}