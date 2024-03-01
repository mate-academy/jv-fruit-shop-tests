package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperationHandler(new FruitDaoImpl());
        Storage.fruitStorage.clear();
    }

    @Test
    void applyOperation_FruitExistsInStorageQuantityUpdated_Ok() {
        Storage.fruitStorage.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 30);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> actual = Map.of("apple", 80);
        Map<String, Integer> expected = Storage.fruitStorage;
        assertEquals(expected, actual, "Return strategy did not update quantity correctly");
    }

    @Test
    void applyOperation_FruitNotInStorageAddedToStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 40);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> actual = Map.of("banana", 40);
        Map<String, Integer> expected = Storage.fruitStorage;
        assertEquals(expected, actual, "Return strategy did not add new fruit correctly");
    }
}
