package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private BalanceHandler balanceHandler;
    private ReturnHandler returnHandler;

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
        returnHandler = new ReturnHandler();
        fruitStorage.clear();
    }

    @Test
    void returnHandler_processingCorrectData_ok() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        returnHandler.handleTransaction(new FruitTransaction("banana", 10,
                FruitTransaction.Operation.RETURN));
        assertEquals(110, fruitStorage.get("banana"));
    }

    @Test
    void returnHandler_processingIncorrectData_notOk() {
        balanceHandler.handleTransaction(new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE));
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.handleTransaction(new FruitTransaction(null, 100,
                        FruitTransaction.Operation.RETURN)));
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.handleTransaction(new FruitTransaction("banana", -10,
                        FruitTransaction.Operation.RETURN)));
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.handleTransaction(new FruitTransaction("banana", 100, null)));
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.handleTransaction(new FruitTransaction("banana", 100,
                        FruitTransaction.Operation.SUPPLY)));
    }
}
