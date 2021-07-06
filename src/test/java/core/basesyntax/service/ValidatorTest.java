package core.basesyntax.service;

import org.junit.Test;

public class ValidatorTest {
    @Test
    public void validateFile_Ok() {
        new FileValidator().validate("r,apple,25");
    }

    @Test(expected = RuntimeException.class)
    public void validateFileWithNegativeQuantity_NotOk() {
        new FileValidator().validate("b,banana,-10");
    }

    @Test(expected = RuntimeException.class)
    public void validateFileWithoutProductAndQuantity_NotOk() {
        new FileValidator().validate("s,");
    }

    @Test(expected = RuntimeException.class)
    public void validateFileWithNotExistOperation_NotOk() {
        new FileValidator().validate("q,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void validateFileEmptyLine_NotOk() {
        new FileValidator().validate("");
    }
}
