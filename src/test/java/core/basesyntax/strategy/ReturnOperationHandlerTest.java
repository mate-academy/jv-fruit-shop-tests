package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void updateFruitStorage_ValidReturnOperation_Ok() {
        FruitStorage.fruitStorage.put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                50
        );
        returnOperationHandler.updateFruitStorage(fruitTransaction, fruitStorageDao);
        int actual = FruitStorage.fruitStorage.get("apple");
        assertEquals(150, actual, "Quantity is expected to be 150");
    }
}
