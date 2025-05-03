package core.basesyntax.service.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NegativeQuantityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitQuantityValidatorImplTest {
    private static final int POSITIVE_QUANTITY = 20;
    private static final int EDGE_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -20;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = new FruitQuantityValidatorImpl();
    }

    @Test
    void validate_positiveQuantity_ok() {
        validator.validate(POSITIVE_QUANTITY);
    }

    @Test
    void validate_edgeQuantity_ok() {
        validator.validate(EDGE_QUANTITY);
    }

    @Test
    void validate_negativeQuantity_notOk() {
        assertThrows(NegativeQuantityException.class,
                () -> validator.validate(NEGATIVE_QUANTITY)
        );
    }
}
