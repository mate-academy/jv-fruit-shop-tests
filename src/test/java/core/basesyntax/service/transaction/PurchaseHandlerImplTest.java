package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerImplTest {
    private static TransactionHandler purchaseHandler;
    private static String code;
    private String fruit;
    private int balance;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandlerImpl();
        code = "p";
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        fruit = "banana";
        balance = 30;
        Storage.fruits.put(fruit, balance);
        Storage.fruits.put("apple", 88);
    }

    @Test
    void purchaseHandler_validTransaction_ok() {
        int quantity = balance - 10;
        FruitTransaction transaction =
                new FruitTransaction(code, fruit, quantity);
        purchaseHandler.handleTransaction(transaction);
        assertEquals(balance - quantity, Storage.fruits.get(fruit));
    }

    @Test
    void purchaseHandler_quantityEqualsBalance_ok() {
        int quantity = balance;
        FruitTransaction transaction =
                new FruitTransaction(code, fruit, quantity);
        purchaseHandler.handleTransaction(transaction);
        assertEquals(0, Storage.fruits.get(fruit));
    }

    @Test
    void purchaseHandler_lowBalance_notOk() {
        int quantity = balance + 1;
        FruitTransaction transaction =
                new FruitTransaction(code, fruit, quantity);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handleTransaction(transaction));
    }
}
