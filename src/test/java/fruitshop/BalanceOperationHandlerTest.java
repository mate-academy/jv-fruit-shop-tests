package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.BalanceOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    private BalanceOperationHandler handler;

    @BeforeEach
    void setUp_cleanStorage() {
        handler = new BalanceOperationHandler();
        Storage.clear();
    }

    @Test
    void apply_correctValues_storageUpdatedOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(100);

        handler.apply(transaction);

        assertEquals(100, Storage.get("apple"));
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(null);
        transaction.setQuantity(50);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }

    @Test
    void apply_transactionWithNegativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");

        assertThrows(IllegalArgumentException.class, () -> {
            transaction.setQuantity(-5);
            handler.apply(transaction);
        });
    }

    @Test
    void setQuantity_negativeQuantity_storageUpdatedNotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");

        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-10));
    }
}

