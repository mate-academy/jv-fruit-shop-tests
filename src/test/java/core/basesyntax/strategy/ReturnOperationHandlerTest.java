package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.fruitStorage.put("banana", 50);
        Storage.fruitStorage.put("apple",25);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_return_Ok() {
        returnOperationHandler.apply(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 25));
        Integer amountAfterReturn = Storage.fruitStorage.get("banana");
        assertEquals(75, amountAfterReturn);
    }
}
