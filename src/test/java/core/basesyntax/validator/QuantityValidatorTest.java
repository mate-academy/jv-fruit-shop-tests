package core.basesyntax.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.exception.FruitShopException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityValidatorTest {
    private static final int INCORRECT_FRUIT_QUANTITY = -1;
    private static final int FIRST_CORRECT_FRUIT_QUANTITY = 0;
    private static final int SECOND_CORRECT_FRUIT_QUANTITY = 1;
    private static QuantityValidator quantityValidator;

    @BeforeEach
    void setUp() {
        quantityValidator = new QuantityValidator();
    }

    @Test
    void getQuantityValidation_incorrectFruitQuantity_notOk() {
        Assert.assertThrows(FruitShopException.class, () ->
                quantityValidator.getQuantityValidation(INCORRECT_FRUIT_QUANTITY));
    }

    @Test
    void getQuantityValidation_correctFruitQuantityWithZero_Ok() {
        assertDoesNotThrow(() ->
                quantityValidator.getQuantityValidation(FIRST_CORRECT_FRUIT_QUANTITY));
    }

    @Test
    void getQuantityValidation_correctFruitQuantity_Ok() {
        assertDoesNotThrow(() ->
                quantityValidator.getQuantityValidation(SECOND_CORRECT_FRUIT_QUANTITY));
    }
}
