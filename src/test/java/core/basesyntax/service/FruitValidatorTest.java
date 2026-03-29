package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitValidatorTest {
    private FruitValidator fruitValidator;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitValidator = new FruitValidator();
    }

    @Test
    void check_validTransactionInput_ok() {
        fruitTransaction = FruitTransaction.of(Operation.B, "orange", 20);
        Assertions.assertTrue(fruitValidator.check(fruitTransaction));
    }

    @Test
    void check_transactionQuantityIsZero_ok() {
        fruitTransaction = FruitTransaction.of(Operation.B, "orange", 0);
        Assertions.assertTrue(fruitValidator.check(fruitTransaction));
    }

    @Test
    void check_transactionIsNull_notOk() {
        Assertions.assertFalse(fruitValidator.check(null));
    }

    @Test
    void check_transactionFruitNameIsNull_notOk() {
        fruitTransaction = FruitTransaction.of(Operation.B, null, 20);
        Assertions.assertFalse(fruitValidator.check(fruitTransaction));
    }

    @Test
    void check_transactionFruitNameIsEmpty_notOk() {
        fruitTransaction = FruitTransaction.of(Operation.B, " ", 20);
        Assertions.assertFalse(fruitValidator.check(fruitTransaction));
    }

    @Test
    void check_transactionFruitQuantityIsLowerThanZero_notOk() {
        fruitTransaction = FruitTransaction.of(Operation.B, "banana", -5);
        Assertions.assertFalse(fruitValidator.check(fruitTransaction));
    }

    @Test
    void check_transactionOperationIsNull_notOk() {
        fruitTransaction = FruitTransaction.of(null, "banana", 20);
        Assertions.assertFalse(fruitValidator.check(fruitTransaction));
    }
}
