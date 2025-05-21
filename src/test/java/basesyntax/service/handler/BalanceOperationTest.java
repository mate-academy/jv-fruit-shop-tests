package basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler balance;

    @BeforeEach
    void setUp() {
        balance = new BalanceOperation();
        Storage.clear();
    }

    @Test
    void handle_addNewFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,"apple", 5);
        balance.handle(fruitTransaction);
        assertEquals(5, Storage.get("apple"));
    }

    @Test
    void handle_updatesQuantityInStorage_Ok() {
        Storage.put("banana", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,"banana", 20);
        balance.handle(fruitTransaction);
        assertEquals(20, Storage.get("banana"));
    }

    @Test
    void handle_zeroQuantity_Ok() {
        Storage.put("apple", 3);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,"apple", 0);
        balance.handle(fruitTransaction);
        assertEquals(0, Storage.get("apple"));
    }

    @Test
    void handle_independentUpdates_Ok() {
        Storage.put("kiwi", 50);
        Storage.put("apple",200);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 350);
        balance.handle(fruitTransaction);
        assertEquals(50, Storage.get("kiwi"));
        assertEquals(350, Storage.get("apple"));
    }
}
