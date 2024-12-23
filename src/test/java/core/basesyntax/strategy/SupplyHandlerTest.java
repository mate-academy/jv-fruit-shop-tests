package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private BalanceHandler balanceHandler;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
        supplyHandler = new SupplyHandler();
        fruitStorage.clear();
    }

    @Test
    void supplyHandler_correctDataProcessing_ok() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        supplyHandler.handleTransaction(new FruitTransaction("banana", 10,
                FruitTransaction.Operation.SUPPLY));
        assertTrue(fruitStorage.containsKey("banana"));
        assertEquals(110, fruitStorage.get("banana"));
        supplyHandler.handleTransaction(new FruitTransaction("apple", 100,
                FruitTransaction.Operation.SUPPLY));
        assertTrue(fruitStorage.containsKey("apple"));
        assertEquals(100, fruitStorage.get("apple"));
    }

    @Test
    void supplyHandler_incorrectDataProcessing_notOk() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.handleTransaction(new FruitTransaction("banana", -1,
                        FruitTransaction.Operation.SUPPLY)));
        assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.handleTransaction(new FruitTransaction(null, 10,
                        FruitTransaction.Operation.SUPPLY)));
        assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.handleTransaction(new FruitTransaction("banana", 10, null)));
        assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.handleTransaction(new FruitTransaction("banana", 10,
                        FruitTransaction.Operation.PURCHASE)));
    }
}
