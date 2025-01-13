package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.clearStorage();
    }

    @Test
    void apply_shouldIncreaseFruitQuantityOnReturn() {
        String fruit = "apple";
        Storage.modifyFruitStorage(fruit, 100);

        int returnQuantity = 50;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, returnQuantity);

        returnOperation.apply(transaction);

        assertEquals(150, Storage.getFruitQuantity(fruit));
    }

    @Test
    void apply_shouldNotChangeStorageIfReturnQuantityIsZero() {
        String fruit = "banana";
        Storage.modifyFruitStorage(fruit, 50);

        int returnQuantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, returnQuantity);

        returnOperation.apply(transaction);

        assertEquals(50, Storage.getFruitQuantity(fruit));
    }

    @Test
    void apply_shouldIncreaseFruitQuantityForMultipleReturns() {
        String fruit = "orange";
        Storage.modifyFruitStorage(fruit, 30);

        int firstReturnQuantity = 20;
        FruitTransaction firstTransaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, firstReturnQuantity);

        int secondReturnQuantity = 10;
        FruitTransaction secondTransaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, secondReturnQuantity);

        returnOperation.apply(firstTransaction);
        returnOperation.apply(secondTransaction);

        assertEquals(60, Storage.getFruitQuantity(fruit));
    }
}
