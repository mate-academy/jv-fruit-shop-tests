package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    @AfterEach
    void afterEach_setUp() {
        Storage.getFruitTransactions().clear();
    }

    @Test
    @DisplayName("Return operation test")
    void calculateQuantityReturnOperation_ok() {
        ReturnOperation returnOperation = new ReturnOperation();
        returnOperation.calculateQuantity(FruitTransaction.FruitName.APPLE, 30);
        int expectedStorageSizeAfterReturnOperation = 1;
        int actualStorageSizeAfterReturnOperation = Storage.getFruitTransactions().size();
        assertEquals(expectedStorageSizeAfterReturnOperation,
                     actualStorageSizeAfterReturnOperation);
    }

    @Test
    @DisplayName("Return operation validate fruitName")
    void calculateQuantityFruitNameNull_notOk() {
        ReturnOperation returnOperation = new ReturnOperation();
        assertThrows(RuntimeException.class, () -> returnOperation.validateFruitName(null));
    }

    @Test
    @DisplayName("Return operation validate fruitName")
    void calculateQuantityNegative_notOk() {
        ReturnOperation returnOperation = new ReturnOperation();
        assertThrows(RuntimeException.class, () -> returnOperation.validateQuantity(-10));
    }
}
