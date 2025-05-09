package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler handler;

    @BeforeEach
    void setUp_handler() {
        handler = new BalanceOperationHandler();
    }

    @Test
    void apply_correctValues_storageUpdatedOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        handler.apply(transaction);
        assertEquals(100, Storage.get("apple"));
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 50);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }

    @Test
    void apply_transactionWithNegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction transaction =
                    new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -5);
        });
    }

    @Test
    void setQuantity_negativeQuantity_storageUpdatedNotOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);

        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-10));
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}

