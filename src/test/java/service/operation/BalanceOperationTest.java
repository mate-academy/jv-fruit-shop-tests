package service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import data.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest extends OperationHandlerTest {
    private static OperationHandler balanceOperation;

    @BeforeAll
    static void createBalanceOperation() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void balanceHandle_valid_ok() {
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == DEFAULT_QUANTITY);
    }

    @Test
    void balanceHandle_duplicateTransaction_ok() {
        balanceOperation.handle(defaultTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == DEFAULT_QUANTITY);
    }

    @Test
    void balanceHandle_zeroQuantity_ok() {
        balanceOperation.handle(zeroQuantityTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == ZERO_QUANTITY);
    }

    @Test
    void balanceHandle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                balanceOperation.handle(negativeQuantityTransaction));
    }

    @Test
    void balanceHandle_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                balanceOperation.handle(nullFruitTransaction));
    }

    @Test
    void balanceHandle_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                balanceOperation.handle(null));
    }
}
