package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerImplTest {
    private static TransactionHandler supplyHandler;
    private static String code;
    private String fruit;
    private int balance;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandlerImpl();
        code = "s";
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
    void returnHandler_validTransaction_ok() {
        int quantity = 10;
        FruitTransaction transaction =
                new FruitTransaction(code, fruit, quantity);
        supplyHandler.handleTransaction(transaction);
        assertEquals(balance + quantity, Storage.fruits.get(fruit));
    }

    @Test
    void returnHandler_returnToEmptyStore_ok() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 88);
        int quantity = 15;
        FruitTransaction transaction =
                new FruitTransaction(code, fruit, quantity);
        supplyHandler.handleTransaction(transaction);
        assertEquals(quantity, Storage.fruits.get(fruit));
    }
}
