package core.basesyntax.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String ACTUAL_FRUIT_NAME = "apple";
    private static final String INVALID_FRUIT_NAME = "invalidFruit";
    private static final String ACTUAL_BALANCE_OPERATION_NAME = "b";
    private static final String INVALID_OPERATION_NAME = "invalidCode";

    @Test
    @DisplayName("Fruit transaction get by fruit name test")
    void getByFruitName_ok() {
        FruitTransaction.FruitName expectedFruit = FruitTransaction.FruitName.APPLE;
        FruitTransaction.FruitName actualFruit = FruitTransaction.FruitName
                .getByFruitName(ACTUAL_FRUIT_NAME);
        assertEquals(expectedFruit, actualFruit);
    }

    @Test
    @DisplayName("Fruit transaction get by non existent fruit name test")
    void getByNonExistentFruitName_notOk() {
        assertThrows(NoSuchElementException.class, () -> FruitTransaction.FruitName
                .getByFruitName(INVALID_FRUIT_NAME));
    }

    @Test
    @DisplayName("Fruit transaction get by fruit name null test")
    void getByFruitNameNull_notOk() {
        assertThrows(NoSuchElementException.class, () -> FruitTransaction.FruitName
                .getByFruitName(null));
    }

    @Test
    @DisplayName("Fruit transaction get by code test")
    void getByCode_Ok() {
        FruitTransaction.Operation expectedFruit = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualFruit = FruitTransaction.Operation
                .getByCode(ACTUAL_BALANCE_OPERATION_NAME);
        assertEquals(expectedFruit, actualFruit);
    }

    @Test
    @DisplayName("Fruit transaction get by non existent code test")
    void getByNonExistentCode_notOk() {
        assertThrows(NoSuchElementException.class, () -> FruitTransaction.Operation
                .getByCode(INVALID_OPERATION_NAME));
    }

    @Test
    @DisplayName("Fruit transaction get by code null test")
    void getByCodeNull_notOk() {
        assertThrows(NoSuchElementException.class, () -> FruitTransaction.Operation
                .getByCode(null));
    }
}
