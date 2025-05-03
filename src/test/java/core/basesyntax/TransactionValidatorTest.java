package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.TransactionValidator;
import org.junit.jupiter.api.Test;

class TransactionValidatorTest {
    private static final String APPLE = "apple";
    private final TransactionValidator validator = new TransactionValidator();

    @Test
    void validate_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(null));
    }

    @Test
    void validate_zeroQuantity_throwsException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, 0);
        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(transaction));
    }

    @Test
    void validate_validTransaction_noException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, 10);
        validator.validate(transaction);
    }
}
