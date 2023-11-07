package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionValidationImplTest {

    private FruitTransactionValidation fruitValidation;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        fruitValidation = new FruitTransactionValidationImpl();
        transaction = new FruitTransaction();
    }

    @Test
    void validateNullTransaction_NotOk() {
        assertThrows(FruitTransactionException.class,
                () -> fruitValidation.validateTransaction(null));
    }

    @Test
    void validateTransactionWithNullFruitValue() {
        transaction.setQuantity(100);
        transaction.setOperation(Operation.BALANCE);
        assertThrows(FruitTransactionException.class,
                () -> fruitValidation.validateTransaction(transaction));
    }

    @Test
    void validateTransactionWithNullQuantityValue() {
        transaction.setFruit("banana");
        transaction.setOperation(Operation.BALANCE);
        assertThrows(FruitTransactionException.class,
                () -> fruitValidation.validateTransaction(transaction));
    }

    @Test
    void validateTransactionWithNegativeQuantity() {
        transaction.setFruit("banana");
        transaction.setQuantity(-100);
        assertThrows(FruitTransactionException.class,
                () -> fruitValidation.validateTransaction(transaction));
    }

    @Test
    void validateTransactionWithAllValidValues_Ok() {
        transaction.setQuantity(100);
        transaction.setFruit("banana");
        transaction.setOperation(Operation.BALANCE);
        assertDoesNotThrow(() -> fruitValidation.validateTransaction(transaction));
    }
}
