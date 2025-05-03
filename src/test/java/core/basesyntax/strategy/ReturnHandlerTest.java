package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    void init() {
        returnHandler = new ReturnHandler();
        fruitStorage.put("banana", 100);
    }

    @AfterEach
    void clean() {
        fruitStorage.clear();
    }

    @Test
    void returnHandler_processingCorrectData_ok() {
        returnHandler.handleTransaction(new FruitTransaction("banana", 10,
                FruitTransaction.Operation.RETURN));
        assertEquals(110, fruitStorage.get("banana"));
    }

    @Test
    void returnHandler_processingIncorrectData_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.handleTransaction(new FruitTransaction("banana", -10,
                        FruitTransaction.Operation.RETURN)));
    }
}
