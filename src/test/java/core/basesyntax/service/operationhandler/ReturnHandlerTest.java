package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        returnHandler = new ReturnHandler();
    }

    @Test
    public void handle_quantityNull_Ok() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        returnHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 43);
    }

    @Test
    public void handle_quantityNotNull_Ok() {
        Storage.fruits.put("banana", 7);
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        returnHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 50);
    }
}
