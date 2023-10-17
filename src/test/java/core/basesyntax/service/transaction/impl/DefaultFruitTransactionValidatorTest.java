package core.basesyntax.service.transaction.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.FruitTransactionValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultFruitTransactionValidatorTest {
    private static final FruitTransaction VALID_FRUIT_TRANSACTION = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "apple", 10
    );
    private static final FruitTransaction INVALID_FRUIT_TRANSACTION = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "apple", -10
    );

    private static FruitTransactionValidator fruitTransactionValidator;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionValidator = new DefaultFruitTransactionValidator();
    }

    @Test
    void isValid_validCase_ok() {
        assertTrue(fruitTransactionValidator.isValid(VALID_FRUIT_TRANSACTION));
    }

    @Test
    void isValid_invalidCase_notOk() {
        assertFalse(fruitTransactionValidator.isValid(INVALID_FRUIT_TRANSACTION));
    }

    @Test
    void isValid_nullInput_notOk() {
        assertFalse(fruitTransactionValidator.isValid(null));
    }
}
