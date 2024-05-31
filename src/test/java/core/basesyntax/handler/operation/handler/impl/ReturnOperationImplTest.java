package core.basesyntax.handler.operation.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationImplTest {
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperationImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    void testOperationTypeAddsNewFruit() {
        FruitModel peach = FruitModel.PEACH;
        int amount = 56;

        returnOperation.operationType(peach, amount);

        assertEquals(56, Storage.fruitStorage.get(peach));
    }

    @Test
    void testOperationTypeUpdatesExistingFruitAmount() {
        FruitModel banana = FruitModel.BANANA;
        Storage.fruitStorage.put(banana, 28);
        int amount = 16;
        returnOperation.operationType(banana, amount);
        assertEquals(44, Storage.fruitStorage.get(banana));
    }
}
