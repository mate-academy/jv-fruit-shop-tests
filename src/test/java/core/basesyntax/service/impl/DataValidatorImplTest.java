package core.basesyntax.service.impl;

import core.basesyntax.service.DataValidator;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataValidatorImplTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private final DataValidator dataValidator = new DataValidatorImpl();

    @Test
    public void validate_valid_ok() {
        dataValidator.validate(List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,57",
                "s,banana,50"));
    }

    @Test
    public void validate_empty_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for empty input file");
        dataValidator.validate(List.of());
    }

    @Test
    public void validate_invalidFirstLine_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate 1 line");
        dataValidator.validate(List.of("aSd,das,dasd", "b,banana,23", "p,banana,13"));
    }
}
