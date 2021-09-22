package core.basesyntax.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ValidatorTest {
    @Test
    public void validate_validValue_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,apple,100");
        assertTrue(Validator.validate(lines));
    }

    @Test
    public void validate_invalidNumberValue_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,-20");
        lines.add("s,apple,100");
        assertFalse(Validator.validate(lines));
    }

    @Test
    public void validate_invalidActivityValue_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("a,banana,20");
        lines.add("s,apple,100");
        assertFalse(Validator.validate(lines));
    }

    @Test
    public void validate_invalidLineSizeValue_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("a,banana,20,30");
        lines.add("s,apple,100");
        assertFalse(Validator.validate(lines));
    }

    @Test
    public void validate_emptyValue_Ok() {
        List<String> lines = new ArrayList<>();
        assertFalse(Validator.validate(lines));
    }
}
