package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.impl.ShopStorage;
import org.junit.jupiter.api.Test;

class ShopHandlerValidatorTest {
    @Test
    void validate_validArguments_ok() {
        assertDoesNotThrow(() ->
                ShopHandlerValidator.validate(new ShopStorage(), "banana", 23));
    }

    @Test
    void validate_storageIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                ShopHandlerValidator.validate(null, "banana", 10));
    }

    @Test
    void validate_productIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                ShopHandlerValidator.validate(new ShopStorage(), null, 10));
    }

    @Test
    void validate_quantityIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                ShopHandlerValidator.validate(new ShopStorage(), "banana", null));
    }

    @Test
    void validate_productIsBlank_notOk() {
        assertThrows(RuntimeException.class, () ->
                ShopHandlerValidator.validate(new ShopStorage(), "   ", 10));
    }

    @Test
    void validate_quantityLessThanZero_notOk() {
        assertThrows(RuntimeException.class, () ->
                ShopHandlerValidator.validate(new ShopStorage(), "banana", -10));
    }
}
