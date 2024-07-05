package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.TransactionValidator;
import org.junit.jupiter.api.Test;

class TransactionValidatorTest {

    private final TransactionValidator validator = new TransactionValidator();

    @Test
    void validate_nullTransaction() {
        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(null));
    }

    @Test
    void validate_zeroQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 0);
        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(transaction));
    }

    @Test
    void validate_validTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        validator.validate(transaction); // Should not throw any exception
    }
}