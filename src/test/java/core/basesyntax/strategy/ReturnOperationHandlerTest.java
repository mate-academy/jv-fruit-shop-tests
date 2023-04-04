package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void handle_quantityNull_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        returnOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 43);
    }

    @Test
    public void handle_quantityNotNull_Ok() {
        Storage.fruits.put("banana", 7);
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        returnOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 50);
    }
}
