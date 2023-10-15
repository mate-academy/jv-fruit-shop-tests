package service.activities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityVerifierImplTest {
    private QuantityVerifier quantityVerifier;

    @BeforeEach
    void setUp() {
        quantityVerifier = new QuantityVerifierImpl();
    }

    @Test
    void quantityVerify_Ok() {
        String fruit = "apple";
        int quantity = 100;
        assertDoesNotThrow(() -> quantityVerifier.quantityVerify(quantity, fruit));
    }

    @Test
    void quantityVerify_NotOk() {
        String fruit = "apple";
        int quantity = -100;
        assertThrows(IllegalArgumentException.class,
                () -> quantityVerifier.quantityVerify(quantity, fruit));
    }
}
