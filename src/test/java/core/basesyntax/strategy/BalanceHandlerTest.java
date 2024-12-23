package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
        fruitStorage.clear();
    }

    @Test
    void balanceHandler_processingCorrectData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE);
        balanceHandler.handleTransaction(fruitTransaction);
        assertEquals(fruitStorage.get("banana"), 100);
        assertEquals(fruitStorage.size(), 1);
    }

    @Test
    void balanceHandler_processingNullData_notOk() {
        FruitTransaction fruitTransaction = null;
        assertThrows(NullPointerException.class,
                () -> balanceHandler.handleTransaction(fruitTransaction));
        assertEquals(fruitStorage.size(), 0);
    }

    @Test
    void balanceHandler_processingIncorrectData_notOk() {
        assertThrows(RuntimeException.class,
                () -> balanceHandler.handleTransaction(new FruitTransaction("banana", -100,
                FruitTransaction.Operation.BALANCE)));
        assertThrows(RuntimeException.class,
                () -> balanceHandler.handleTransaction(new FruitTransaction(null, 100,
                        FruitTransaction.Operation.BALANCE)));
        assertThrows(IllegalArgumentException.class,
                () -> balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                        FruitTransaction.Operation.PURCHASE)));
    }
}
