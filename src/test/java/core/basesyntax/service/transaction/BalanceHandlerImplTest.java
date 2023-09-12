package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerImplTest {
    private static TransactionHandler balanceHandler;
    private static String balanceCode;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandlerImpl();
        balanceCode = "b";
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void balanceHandler_setNewItem_ok() {
        String fruit = "banana";
        int quantity = 20;
        FruitTransaction transaction =
                new FruitTransaction(balanceCode, fruit, quantity);
        balanceHandler.handleTransaction(transaction);
        assertEquals(Map.of(fruit, quantity), Storage.fruits);
    }

    @Test
    void balanceHandler_setExistingItem_notOk() {
        String fruit = "banana";
        int quantity = 20;
        Storage.fruits.put(fruit, quantity);
        FruitTransaction transaction =
                new FruitTransaction(balanceCode, fruit, quantity);
        assertThrows(RuntimeException.class, () -> balanceHandler.handleTransaction(transaction));
    }
}
