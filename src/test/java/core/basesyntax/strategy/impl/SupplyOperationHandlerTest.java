package core.basesyntax.strategy.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static core.basesyntax.util.FruitTestConstants.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperationHandler(new FruitDaoImpl());
        Storage.fruitStorage.clear();
    }

    @Test
    void applyOperation_FruitExistsInStorageQuantityUpdated_Ok() {
        Storage.fruitStorage.put(APPLE, 50);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, APPLE, 30);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> actual = Map.of(APPLE, 80);
        Map<String, Integer> expected = Storage.fruitStorage;
        assertEquals(expected, actual, "Supply strategy did not update quantity correctly");
    }

    @Test
    void applyOperation_FruitNotInStorageAddedToStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, BANANA, 40);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> actual = Map.of(BANANA, 40);
        Map<String, Integer> expected = Storage.fruitStorage;
        assertEquals(expected, actual, "Supply strategy did not add new fruit correctly");
    }

    @Test
    void applyOperation_InvalidQuantity_NotOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, BANANA, -40);
        assertThrows(RuntimeException.class, () -> operationHandler.applyOperation(transaction),
                "Supply strategy did not throw exception for invalid quantity");
    }
}
