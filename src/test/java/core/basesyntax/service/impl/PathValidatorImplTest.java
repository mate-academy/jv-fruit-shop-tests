package core.basesyntax.service.impl;

import core.basesyntax.service.PathValidator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PathValidatorImplTest {

    @Test
    void filePathValidator_ok() {
        String valid = "src/main/java/core/basesyntax/resources/output.csv";
        PathValidator path = new PathValidatorImpl();
        Assert.assertTrue(path.filePathValidator(valid));
    }

    @Test
    void filePathValidator_notOk() {
        String valid = "src/main?resources/output.csv";
        PathValidator path = new PathValidatorImpl();
        Assert.assertFalse(path.filePathValidator(valid));
    }
}
