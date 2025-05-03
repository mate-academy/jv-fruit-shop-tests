package service.activities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityVerifierImplTest {
    public static final int QUANTITY_NEGATIVE_HUNDRED = -100;
    public static final int QUANTITY_NEGATIVE_ONE = -1;
    public static final int QUANTITY_ZERO = 0;
    public static final int QUANTITY_ONE = 1;
    public static final int QUANTITY_TEN = 10;
    public static final int QUANTITY_HUNDRED = 100;
    private QuantityVerifier quantityVerifier;

    @BeforeEach
    void setUp() {
        quantityVerifier = new QuantityVerifierImpl();
    }

    @Test
    void quantityVerify_Ok() {
        String fruit = "apple";
        assertDoesNotThrow(() -> quantityVerifier.quantityVerify(QUANTITY_ZERO, fruit));
        assertDoesNotThrow(() -> quantityVerifier.quantityVerify(QUANTITY_ONE, fruit));
        assertDoesNotThrow(() -> quantityVerifier.quantityVerify(QUANTITY_TEN, fruit));
        assertDoesNotThrow(() -> quantityVerifier.quantityVerify(QUANTITY_HUNDRED, fruit));
    }

    @Test
    void quantityVerify_NotOk() {
        String fruit = "apple";
        assertThrows(IllegalArgumentException.class,
                () -> quantityVerifier.quantityVerify(QUANTITY_NEGATIVE_ONE, fruit));
        assertThrows(IllegalArgumentException.class,
                () -> quantityVerifier.quantityVerify(QUANTITY_NEGATIVE_HUNDRED, fruit));
    }
}
