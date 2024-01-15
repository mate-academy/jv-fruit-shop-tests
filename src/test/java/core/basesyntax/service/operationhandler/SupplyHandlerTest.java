package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private SupplyHandler supplyHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void handle_quantityNull_Ok() {
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 44);
        supplyHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 44);
    }

    @Test
    public void handle_quantityNotNull_Ok() {
        Storage.fruits.put("banana", 7);
        FruitTransaction item = new FruitTransaction(Operation.RETURN,
                "banana", 43);
        supplyHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 50);
    }
}
