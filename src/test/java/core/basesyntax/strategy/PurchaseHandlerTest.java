package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private BalanceHandler balanceHandler;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
        purchaseHandler = new PurchaseHandler();
        fruitStorage.clear();
    }

    @Test
    void purchaseHandler_processingCorrectData_ok() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        purchaseHandler.handleTransaction(new FruitTransaction("banana", 10,
                FruitTransaction.Operation.PURCHASE));
        assertEquals(fruitStorage.get("banana"), 90);
    }

    @Test
    void purchaseHandler_processingIncorrectTransaction_notOk() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", 101,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("apple", 100,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", -10,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", 12,
                        FruitTransaction.Operation.BALANCE)));
    }

    @Test
    void purchaseHandler_corruptedTransaction_notOk() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction(null, 10,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", 10,
                        null)));
    }
}
