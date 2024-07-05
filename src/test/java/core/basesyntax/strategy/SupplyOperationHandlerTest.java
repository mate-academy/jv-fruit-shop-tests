package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler supplyOperationHandler;

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.fruitStorage.put("banana", 50);
        Storage.fruitStorage.put("apple",25);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_supply_ok() {
        supplyOperationHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",30));
        Integer amountAfterSupply = Storage.fruitStorage.get("banana");
        assertEquals(80,amountAfterSupply);
    }
}
