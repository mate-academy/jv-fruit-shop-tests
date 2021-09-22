package core.basesyntax.service.validator;

import static org.junit.Assert.assertThrows;

import org.junit.BeforeClass;
import org.junit.Test;

public class FirstLineValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new FirstLineValidatorImpl();
    }

    @Test
    public void firstLineValidatorImpl_wrong_length_line_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("type,fruit,quantity,777"));
    }

    @Test
    public void firstLineValidatorImpl_wrong_first_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("type777,fruit,quantity"));
    }

    @Test
    public void firstLineValidatorImpl_wrong_second_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("type,fruit777,quantity"));
    }

    @Test
    public void firstLineValidatorImpl_wrong_third_word_notOk() {
        assertThrows(RuntimeException.class, () -> validator.valid("type,fruit,quantity777"));
    }

    @Test
    public void firstLineValidatorImpl_right_line_Ok() {
        validator.valid("type,fruit,quantity");
    }
}
