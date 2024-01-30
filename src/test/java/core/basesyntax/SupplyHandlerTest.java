package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.db.Storage;
import core.basesyntax.fruitshop.model.FruitTransaction;
import core.basesyntax.fruitshop.strategy.impl.SupplyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private final SupplyHandler supplyHandler = new SupplyHandler();
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFruitStorage().clear();
    }

    @Test
    void handleSupplyOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 25);
        supplyHandler.handle(transaction);
        assertEquals(25, storage.getFruitStorage().get("banana").intValue(),
                "Supply operation should increase stock");
    }
}
