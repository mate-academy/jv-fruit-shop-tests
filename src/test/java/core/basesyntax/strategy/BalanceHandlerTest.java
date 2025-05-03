package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
    }

    @AfterEach
    void clear() {
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
    void balanceHandler_processingIncorrectData_notOk() {
        assertThrows(RuntimeException.class,
                () -> balanceHandler.handleTransaction(new FruitTransaction("banana", -100,
                FruitTransaction.Operation.BALANCE)));
    }
}
