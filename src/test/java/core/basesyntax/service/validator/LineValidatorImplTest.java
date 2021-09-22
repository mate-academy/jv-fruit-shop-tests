package core.basesyntax.service.validator;

import static org.junit.Assert.assertThrows;

import org.junit.BeforeClass;
import org.junit.Test;

public class LineValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new LineValidatorImpl();
    }

    @Test
    public void lineValidatorImpl_wrong_length_line_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("b,banana,777,777"));
    }

    @Test
    public void lineValidatorImpl_wrong_first_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("zzz,banana,777"));
    }

    @Test
    public void lineValidatorImpl_wrong_second_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("b,zzz,777"));
    }

    @Test
    public void lineValidatorImpl_wrong_third_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("b,banana,zzz"));
    }

    @Test
    public void lineValidatorImpl_right_line_Ok() {
        validator.valid("b,banana,7");
    }
}
