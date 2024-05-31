package core.basesyntax.handler.operation.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationImplTest {
    private SupplyOperationImpl supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperationImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    void testOperationTypeAddsNewFruit() {
        FruitModel pineapple = FruitModel.PINEAPPLE;
        int amount = 52;
        supplyOperation.operationType(pineapple, amount);
        assertEquals(52, Storage.fruitStorage.get(pineapple));
    }

    @Test
    void testOperationTypeUpdatesExistingFruitAmount() {
        FruitModel lime = FruitModel.LIME;
        Storage.fruitStorage.put(lime, 8);
        int amount = 49;

        supplyOperation.operationType(lime, amount);

        assertEquals(57, Storage.fruitStorage.get(lime));
    }
}
